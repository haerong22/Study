import { useState, useEffect, useRef, useCallback } from 'react';
import { WebSocketMessage } from '../types';

interface UseWebSocketProps {
    userId: number;
    onMessage?: (message: WebSocketMessage) => void;
    onConnect?: () => void;
    onDisconnect?: () => void;
    onError?: (error: string) => void;
}

interface UseWebSocketReturn {
    isConnected: boolean;
    lastMessage: WebSocketMessage | null;
    sendMessage: (message: WebSocketMessage) => boolean;
    connect: () => void;
    disconnect: () => void;
    error?: string;
}

export const useWebSocket = ({
                                 userId,
                                 onMessage,
                                 onConnect,
                                 onDisconnect,
                                 onError,
                             }: UseWebSocketProps): UseWebSocketReturn => {
    const wsRef = useRef<WebSocket | null>(null);
    const [isConnected, setIsConnected] = useState(false);
    const [lastMessage, setLastMessage] = useState<WebSocketMessage | null>(null);
    const [error, setError] = useState<string | undefined>(undefined);
    const reconnectAttempts = useRef(0);
    const reconnectTimeoutRef = useRef<number>();
    const maxReconnectAttempts = 5;

    // onMessage 콜백을 ref로 관리하여 항상 최신 함수를 참조하도록 함
    const onMessageRef = useRef(onMessage);
    useEffect(() => {
        onMessageRef.current = onMessage;
    }, [onMessage]);

    const onConnectRef = useRef(onConnect);
    useEffect(() => {
        onConnectRef.current = onConnect;
    }, [onConnect]);

    const onDisconnectRef = useRef(onDisconnect);
    useEffect(() => {
        onDisconnectRef.current = onDisconnect;
    }, [onDisconnect]);

    const onErrorRef = useRef(onError);
    useEffect(() => {
        onErrorRef.current = onError;
    }, [onError]);

    /**
     * 커넥션 연결
     */
    const connect = useCallback(() => {
        if (!userId) {
            console.warn("WebSocket connect: userId is missing, connection deferred.");
            return;
        }

        if (wsRef.current?.readyState === WebSocket.OPEN) {
            console.log('WebSocket already connected.');
            return;
        }

        // 이전 연결이 있다면 정리
        if (wsRef.current) {
            wsRef.current.close();
            wsRef.current = null;
        }

        try {
            // Nginx를 통한 WebSocket 연결
            const wsUrl = `ws://localhost/api/ws/chat?userId=${userId}`;

            wsRef.current = new WebSocket(wsUrl);

            wsRef.current.onopen = (event) => {
                console.log('✅ WebSocket connected');
                setIsConnected(true);
                setError(undefined);
                reconnectAttempts.current = 0;
                onConnectRef.current?.();
            };

            wsRef.current.onmessage = (event) => {
                console.log('🔌 WebSocket message received:', event.data);
                try {
                    const message: WebSocketMessage = JSON.parse(event.data);
                    setLastMessage(message);
                    onMessageRef.current?.(message);
                } catch (err) {
                    console.error('Failed to parse WebSocket message:', err);
                    setError('메시지 파싱 실패');
                }
            };

            wsRef.current.onclose = (event) => {
                console.log(`🔌 WebSocket disconnected: Code=${event.code}, Reason=${event.reason}`);
                setIsConnected(false);
                wsRef.current = null;
                onDisconnectRef.current?.();

                // 정상적인 종료가 아닌 경우만 재연결 시도
                if (event.code !== 1000 && event.code !== 1001 && reconnectAttempts.current < maxReconnectAttempts) {
                    const delay = Math.min(1000 * Math.pow(2, reconnectAttempts.current), 30000);

                    // 기존 타이머 정리
                    if (reconnectTimeoutRef.current) {
                        clearTimeout(reconnectTimeoutRef.current);
                    }

                    reconnectTimeoutRef.current = setTimeout(() => {
                        reconnectAttempts.current++;
                        connect();
                    }, delay);
                } else if (reconnectAttempts.current >= maxReconnectAttempts) {
                    setError('최대 재연결 시도 횟수를 초과했습니다.');
                }
            };

            wsRef.current.onerror = (event) => {
                console.error('💥 WebSocket error:', event);
                setError('WebSocket 연결 오류');
                setIsConnected(false);
                onErrorRef.current?.(event.type);
            };

        } catch (err) {
            console.error('Failed to create WebSocket connection:', err);
            setError('WebSocket 연결 생성 실패');
        }
    }, [userId]);

    const disconnect = useCallback(() => {
        reconnectAttempts.current = 0; // 수동 disconnect 시 재연결 시도 초기화
        if (reconnectTimeoutRef.current) {
            clearTimeout(reconnectTimeoutRef.current);
        }

        if (wsRef.current) {
            wsRef.current.close(1000, 'User disconnected');
            wsRef.current = null;
        }

        setIsConnected(false);
    }, []);

    const sendMessage = useCallback((message: WebSocketMessage) => {
        if (wsRef.current?.readyState === WebSocket.OPEN) {
            try {
                const messageStr = JSON.stringify(message);
                wsRef.current.send(messageStr);
                return true; // 전송 성공
            } catch (err) {
                console.error('Failed to send WebSocket message:', err);
                setError('메시지 전송 실패');
                return false; // 전송 실패
            }
        } else {
            console.warn('WebSocket is not connected');
            setError('WebSocket이 연결되지 않았습니다');
            return false; // 연결되지 않음
        }
    }, []);

    // 컴포넌트 마운트 시 자동 연결 (userId 변경 시에도 재연결)
    useEffect(() => {
        connect();

        return () => {
            disconnect();
        };
    }, [userId, connect, disconnect]); // userId가 변경되면 재연결

    // 페이지 언로드 시 정리
    useEffect(() => {
        const handleBeforeUnload = () => {
            disconnect();
        };

        window.addEventListener('beforeunload', handleBeforeUnload);
        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, [disconnect]);

    return {
        isConnected,
        lastMessage,
        sendMessage,
        connect,
        disconnect,
        error,
    };
};