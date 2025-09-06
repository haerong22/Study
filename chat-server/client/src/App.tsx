import React, { useState, useEffect, useCallback } from 'react';
import { User, ChatRoom, Notification } from './types/index';
import LoginForm from './components/LoginForm.tsx';
import ChatRoomList from './components/ChatRoomList.tsx';
import { ChatWindow } from './components/ChatWindow.tsx';
import { healthApi } from './services/api.ts';
import { theme } from './styles/theme.ts';
import {
    AlertCircle,
    CheckCircle,
    X,
    LogOut
} from 'lucide-react';

function App() {
    const [currentUser, setCurrentUser] = useState<User | null>(null);
    const [selectedChatRoom, setSelectedChatRoom] = useState<ChatRoom | null>(null);
    const [notifications, setNotifications] = useState<Notification[]>([]);
    const [serverStatus, setServerStatus] = useState<'checking' | 'online' | 'offline'>('checking');
    const [lastErrorTime, setLastErrorTime] = useState<number>(0);
    const [healthCheckInterval, setHealthCheckInterval] = useState<number>(30000);
    const [consecutiveErrors, setConsecutiveErrors] = useState<number>(0);
    const [isInitializing, setIsInitializing] = useState<boolean>(true);

    // localStorage 키 상수
    const STORAGE_KEYS = {
        USER: 'chat_current_user',
        SELECTED_ROOM: 'chat_selected_room'
    };

    // localStorage에서 사용자 정보 불러오기
    const loadUserFromStorage = useCallback(() => {
        try {
            const savedUser = localStorage.getItem(STORAGE_KEYS.USER);
            const savedRoom = localStorage.getItem(STORAGE_KEYS.SELECTED_ROOM);

            if (savedUser) {
                const user = JSON.parse(savedUser);
                setCurrentUser(user);

                if (savedRoom) {
                    const room = JSON.parse(savedRoom);
                    setSelectedChatRoom(room);
                }
            }
        } catch (error) {
            console.error('Failed to load user from storage:', error);
            // 손상된 데이터 제거
            localStorage.removeItem(STORAGE_KEYS.USER);
            localStorage.removeItem(STORAGE_KEYS.SELECTED_ROOM);
        } finally {
            setIsInitializing(false);
        }
    }, []);

    // localStorage에 사용자 정보 저장
    const saveUserToStorage = useCallback((user: User | null) => {
        try {
            if (user) {
                localStorage.setItem(STORAGE_KEYS.USER, JSON.stringify(user));
            } else {
                localStorage.removeItem(STORAGE_KEYS.USER);
                localStorage.removeItem(STORAGE_KEYS.SELECTED_ROOM);
            }
        } catch (error) {
            console.error('Failed to save user to storage:', error);
        }
    }, []);

    // localStorage에 선택된 채팅방 저장
    const saveSelectedRoomToStorage = useCallback((room: ChatRoom | null) => {
        try {
            if (room && currentUser) {
                localStorage.setItem(STORAGE_KEYS.SELECTED_ROOM, JSON.stringify(room));
            } else {
                localStorage.removeItem(STORAGE_KEYS.SELECTED_ROOM);
            }
        } catch (error) {
            console.error('Failed to save selected room to storage:', error);
        }
    }, [currentUser]);

    // 앱 초기화 시 localStorage에서 데이터 로드
    useEffect(() => {
        loadUserFromStorage();
    }, [loadUserFromStorage]);

    // 서버 상태 확인
    const checkServerHealth = useCallback(async () => {
        try {
            await healthApi.check();
            setServerStatus('online');
            setConsecutiveErrors(0);
            setHealthCheckInterval(30000); // 성공 시 원래 간격으로 복원
        } catch (error) {
            setServerStatus('offline');
            setConsecutiveErrors(prev => {
                const newErrorCount = prev + 1;
                // 연속 에러 시 헬스체크 간격을 점진적으로 늘림 (최대 5분)
                const newInterval = Math.min(30000 * Math.pow(1.5, newErrorCount), 300000);
                setHealthCheckInterval(newInterval);
                return newErrorCount;
            });

            // 마지막 에러로부터 30초 이상 지났을 때만 새 알림 표시
            setLastErrorTime(prev => {
                const now = Date.now();
                if (now - prev > 30000) {
                    addNotification({
                        id: now.toString(),
                        type: 'error',
                        title: '서버 연결 오류',
                        message: '서버에 연결할 수 없습니다. 잠시 후 자동으로 재시도됩니다.',
                        timestamp: now,
                        read: false,
                    });
                    return now;
                }
                return prev;
            });
        }
    }, []); // 의존성 제거하고 내부에서 setState 함수 사용

    // 알림 추가 (중복 방지)
    const addNotification = (notification: Notification) => {
        setNotifications(prev => {
            // 같은 타입과 제목의 알림이 이미 있는지 확인
            const isDuplicate = prev.some(n =>
                n.type === notification.type &&
                n.title === notification.title &&
                Date.now() - n.timestamp < 30000 // 30초 이내의 중복만 체크
            );

            if (isDuplicate) {
                return prev; // 중복이면 추가하지 않음
            }

            // 최대 3개만 유지 (에러 알림 스팸 방지)
            return [notification, ...prev.slice(0, 2)];
        });

        // 자동 제거 시간 설정
        const autoRemoveTime = notification.type === 'error' ? 10000 : 3000; // 에러는 10초, 나머지는 3초
        setTimeout(() => {
            removeNotification(notification.id);
        }, autoRemoveTime);
    };

    // 알림 제거
    const removeNotification = (id: string) => {
        setNotifications(prev => prev.filter(n => n.id !== id));
    };

    // 에러 처리 (중복 방지)
    const handleError = useCallback((errorMessage: string) => {
        addNotification({
            id: Date.now().toString(),
            type: 'error',
            title: '오류',
            message: errorMessage,
            timestamp: Date.now(),
            read: false,
        });
    }, []);

    // 성공 메시지
    const handleSuccess = useCallback((message: string) => {
        addNotification({
            id: Date.now().toString(),
            type: 'system',
            title: '성공',
            message,
            timestamp: Date.now(),
            read: false,
        });
    }, []);

    // 로그인 처리
    const handleLogin = useCallback((user: User) => {
        setCurrentUser(user);
        saveUserToStorage(user);
        handleSuccess(`${user.displayName}님, 환영합니다!`);
    }, [saveUserToStorage, handleSuccess]);

    // 로그아웃 처리
    const handleLogout = useCallback(() => {
        setCurrentUser(null);
        setSelectedChatRoom(null);
        setNotifications([]);
        saveUserToStorage(null);
        handleSuccess('로그아웃되었습니다.');
    }, [saveUserToStorage, handleSuccess]);

    // 채팅방 선택 처리
    const handleChatRoomSelect = useCallback((chatRoom: ChatRoom) => {
        setSelectedChatRoom(chatRoom);
        saveSelectedRoomToStorage(chatRoom);
    }, [saveSelectedRoomToStorage]);

    useEffect(() => {
        checkServerHealth();
        // 동적 간격으로 서버 상태 확인
        const interval = setInterval(checkServerHealth, healthCheckInterval);
        return () => clearInterval(interval);
    }, [healthCheckInterval]); // checkServerHealth 의존성 제거

    // 스타일 정의
    const appStyle: React.CSSProperties = {
        display: 'flex',
        height: '100vh',
        fontFamily: theme.fonts.primary,
        backgroundColor: theme.colors.background,
        overflow: 'hidden',
    };

    const loginContainerStyle: React.CSSProperties = {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100vh',
        width: '100vw',
        background: `linear-gradient(135deg, ${theme.colors.primary} 0%, ${theme.colors.secondary} 100%)`,
        padding: theme.spacing.lg,
    };

    const chatLayoutStyle: React.CSSProperties = {
        display: 'flex',
        width: '100%',
        height: '100vh',
        overflow: 'hidden',
    };

    const welcomeStyle: React.CSSProperties = {
        flex: 1,
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: theme.colors.backgroundTertiary,
        color: theme.colors.textSecondary,
        padding: theme.spacing.xl,
    };

    const welcomeIconStyle: React.CSSProperties = {
        fontSize: '64px',
        marginBottom: theme.spacing.lg,
    };

    const welcomeTitleStyle: React.CSSProperties = {
        fontSize: theme.fontSizes['2xl'],
        fontWeight: theme.fontWeights.bold,
        color: theme.colors.textPrimary,
        margin: `0 0 ${theme.spacing.sm}`,
    };

    const welcomeDescriptionStyle: React.CSSProperties = {
        fontSize: theme.fontSizes.md,
        color: theme.colors.textSecondary,
        textAlign: 'center',
        lineHeight: theme.lineHeights.relaxed,
        maxWidth: '400px',
    };

    const statusBarStyle: React.CSSProperties = {
        position: 'fixed',
        top: theme.spacing.md,
        left: theme.spacing.md,
        padding: `${theme.spacing.xs} ${theme.spacing.sm}`,
        borderRadius: theme.borderRadius.full,
        fontSize: theme.fontSizes.sm,
        fontWeight: theme.fontWeights.medium,
        zIndex: theme.zIndex.fixed,
        display: 'flex',
        alignItems: 'center',
        gap: theme.spacing.xs,
        backgroundColor: serverStatus === 'online' ? theme.colors.success :
            serverStatus === 'offline' ? theme.colors.error : theme.colors.warning,
        color: theme.colors.white,
        boxShadow: theme.shadows.md,
    };

    const logoutButtonStyle: React.CSSProperties = {
        position: 'fixed',
        top: theme.spacing.md,
        right: theme.spacing.md,
        padding: `${theme.spacing.xs} ${theme.spacing.sm}`,
        backgroundColor: theme.colors.white,
        color: theme.colors.textSecondary,
        border: `1px solid ${theme.colors.border}`,
        borderRadius: theme.borderRadius.md,
        cursor: 'pointer',
        fontSize: theme.fontSizes.sm,
        zIndex: theme.zIndex.fixed,
        display: 'flex',
        alignItems: 'center',
        gap: theme.spacing.xs,
        transition: theme.transitions.normal,
        boxShadow: theme.shadows.sm,
    };

    const notificationsContainerStyle: React.CSSProperties = {
        position: 'fixed',
        top: theme.spacing.md,
        right: theme.spacing.md,
        zIndex: theme.zIndex.tooltip,
        display: 'flex',
        flexDirection: 'column',
        gap: theme.spacing.xs,
        maxWidth: '400px',
    };

    const notificationStyle = (type: string): React.CSSProperties => ({
        padding: theme.spacing.md,
        borderRadius: theme.borderRadius.md,
        boxShadow: theme.shadows.lg,
        border: `1px solid ${theme.colors.border}`,
        backgroundColor: theme.colors.white,
        display: 'flex',
        alignItems: 'flex-start',
        gap: theme.spacing.sm,
        maxWidth: '100%',
    });

    const notificationIconStyle = (type: string): React.CSSProperties => ({
        color: type === 'error' ? theme.colors.error :
            type === 'system' ? theme.colors.success :
                theme.colors.primary,
        flexShrink: 0,
        marginTop: '2px',
    });

    const notificationContentStyle: React.CSSProperties = {
        flex: 1,
        minWidth: 0,
    };

    const notificationTitleStyle: React.CSSProperties = {
        fontWeight: theme.fontWeights.semibold,
        color: theme.colors.textPrimary,
        fontSize: theme.fontSizes.sm,
        marginBottom: theme.spacing.xs,
    };

    const notificationMessageStyle: React.CSSProperties = {
        fontSize: theme.fontSizes.sm,
        color: theme.colors.textSecondary,
        lineHeight: theme.lineHeights.normal,
    };

    const notificationCloseStyle: React.CSSProperties = {
        cursor: 'pointer',
        color: theme.colors.textTertiary,
        flexShrink: 0,
        padding: '2px',
        borderRadius: theme.borderRadius.sm,
        transition: theme.transitions.fast,
    };

    const getNotificationIcon = (type: string) => {
        switch (type) {
            case 'error':
                return <AlertCircle size={16} />;
            case 'system':
                return <CheckCircle size={16} />;
            default:
                return <AlertCircle size={16} />;
        }
    };

    // 초기화 로딩 화면
    if (isInitializing) {
        return (
            <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh',
                backgroundColor: theme.colors.background,
                fontFamily: theme.fonts.primary,
            }}>
                <div style={{
                    textAlign: 'center',
                    padding: theme.spacing.xl,
                }}>
                    <div style={{
                        fontSize: '48px',
                        marginBottom: theme.spacing.lg,
                        animation: 'pulse 2s infinite',
                    }}>
                        💬
                    </div>
                    <h2 style={{
                        color: theme.colors.textPrimary,
                        fontSize: theme.fontSizes.xl,
                        fontWeight: theme.fontWeights.semibold,
                        marginBottom: theme.spacing.sm,
                    }}>
                        채팅 앱 로딩 중...
                    </h2>
                    <p style={{
                        color: theme.colors.textSecondary,
                        fontSize: theme.fontSizes.md,
                    }}>
                        잠시만 기다려주세요
                    </p>
                </div>
            </div>
        );
    }

    return (
        <div style={appStyle}>

            {/* 알림 */}
            {notifications.length > 0 && (
                <div style={notificationsContainerStyle}>
                    {notifications.map((notification) => (
                        <div
                            key={notification.id}
                            style={notificationStyle(notification.type)}
                            className="animate-slideInFromRight"
                        >
                            <div style={notificationIconStyle(notification.type)}>
                                {getNotificationIcon(notification.type)}
                            </div>
                            <div style={notificationContentStyle}>
                                <div style={notificationTitleStyle}>
                                    {notification.title}
                                </div>
                                <div style={notificationMessageStyle}>
                                    {notification.message}
                                </div>
                            </div>
                            <button
                                onClick={() => removeNotification(notification.id)}
                                style={notificationCloseStyle}
                            >
                                <X size={14} />
                            </button>
                        </div>
                    ))}
                </div>
            )}

            {/* 로그아웃 버튼 */}
            {currentUser && (
                <button
                    onClick={handleLogout}
                    style={logoutButtonStyle}
                    className="animate-fadeIn"
                    onMouseEnter={(e) => {
                        e.currentTarget.style.backgroundColor = theme.colors.gray50;
                    }}
                    onMouseLeave={(e) => {
                        e.currentTarget.style.backgroundColor = theme.colors.white;
                    }}
                >
                    <LogOut size={14} />
                    로그아웃
                </button>
            )}

            {/* 메인 컨텐츠 */}
            {!currentUser ? (
                // 로그인 화면
                <div style={loginContainerStyle}>
                    {serverStatus === 'offline' ? (
                        <div style={{
                            textAlign: 'center',
                            padding: theme.spacing.xl,
                            backgroundColor: theme.colors.white,
                            borderRadius: theme.borderRadius.lg,
                            boxShadow: theme.shadows.lg,
                            maxWidth: '400px',
                        }}>
                            <div style={{
                                fontSize: '48px',
                                marginBottom: theme.spacing.md,
                            }}>
                                🔌
                            </div>
                            <h2 style={{
                                color: theme.colors.textPrimary,
                                fontSize: theme.fontSizes.xl,
                                fontWeight: theme.fontWeights.semibold,
                                marginBottom: theme.spacing.sm,
                            }}>
                                서버에 연결할 수 없습니다
                            </h2>
                            <p style={{
                                color: theme.colors.textSecondary,
                                fontSize: theme.fontSizes.md,
                                marginBottom: theme.spacing.lg,
                                lineHeight: theme.lineHeights.relaxed,
                            }}>
                                서버가 실행되지 않았거나 네트워크에 문제가 있습니다.<br/>
                                서버를 실행한 후 페이지를 새로고침해주세요.
                            </p>
                            <button
                                onClick={() => window.location.reload()}
                                style={{
                                    padding: `${theme.spacing.sm} ${theme.spacing.lg}`,
                                    backgroundColor: theme.colors.primary,
                                    color: theme.colors.white,
                                    border: 'none',
                                    borderRadius: theme.borderRadius.md,
                                    fontSize: theme.fontSizes.md,
                                    fontWeight: theme.fontWeights.medium,
                                    cursor: 'pointer',
                                    transition: theme.transitions.normal,
                                }}
                            >
                                새로고침
                            </button>
                        </div>
                    ) : (
                        <LoginForm
                            onLogin={handleLogin}
                            onError={handleError}
                        />
                    )}
                </div>
            ) : (
                // 채팅 화면
                <div style={chatLayoutStyle}>
                    <ChatRoomList
                        currentUser={currentUser}
                        selectedChatRoom={selectedChatRoom || undefined}
                        onChatRoomSelect={handleChatRoomSelect}
                        onError={handleError}
                    />
                    {selectedChatRoom ? (
                        <ChatWindow
                            chatRoom={selectedChatRoom}
                            currentUser={currentUser}
                            onError={handleError}
                        />
                    ) : (
                        <div style={welcomeStyle} className="animate-fadeIn">
                            <div style={welcomeIconStyle}>💬</div>
                            <h2 style={welcomeTitleStyle}>채팅을 시작해보세요!</h2>
                            <p style={welcomeDescriptionStyle}>
                                왼쪽에서 채팅방을 선택하거나 새로운 채팅방을 만들어서
                                실시간 대화를 시작할 수 있습니다.
                            </p>

                            <div style={{
                                marginTop: theme.spacing.xl,
                                padding: theme.spacing.lg,
                                backgroundColor: theme.colors.white,
                                borderRadius: theme.borderRadius.lg,
                                border: `1px solid ${theme.colors.border}`,
                                maxWidth: '350px',
                                textAlign: 'left',
                            }}>
                                <h4 style={{
                                    margin: `0 0 ${theme.spacing.md} 0`,
                                    color: theme.colors.textPrimary,
                                    fontSize: theme.fontSizes.md,
                                    fontWeight: theme.fontWeights.semibold,
                                }}>
                                    🚀 주요 기능
                                </h4>
                                <ul style={{
                                    margin: 0,
                                    paddingLeft: theme.spacing.md,
                                    fontSize: theme.fontSizes.sm,
                                    color: theme.colors.textSecondary,
                                    lineHeight: theme.lineHeights.relaxed,
                                }}>
                                    <li>실시간 메시지 전송</li>
                                    <li>타이핑 인디케이터</li>
                                    <li>채팅방 생성 및 관리</li>
                                    <li>분산 서버 지원</li>
                                    <li>커서 기반 페이징</li>
                                </ul>
                            </div>
                        </div>
                    )}
                </div>
            )}
        </div>
    );
}

export default App;