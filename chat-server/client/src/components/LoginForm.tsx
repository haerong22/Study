import React, { useState } from 'react';
import { User, LoginRequest, RegisterRequest } from '../types/index';
import { userApi } from '../services/api.ts';
import { theme } from '../styles/theme.ts';
import Button from './ui/Button.tsx';
import Input from './ui/Input.tsx';
import { MessageCircle, UserPlus, LogIn, Mail, Lock, User as UserIcon } from 'lucide-react';

interface LoginFormProps {
    onLogin: (user: User) => void;
    onError: (error: string) => void;
}

const LoginForm: React.FC<LoginFormProps> = ({ onLogin, onError }) => {
    const [isLogin, setIsLogin] = useState(true);
    const [loading, setLoading] = useState(false);
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        displayName: '',
    });

    const handleInputChange = (field: string, value: string) => {
        setFormData(prev => ({
            ...prev,
            [field]: value
        }));
    };

    const validateForm = () => {
        if (!formData.username.trim()) {
            onError('사용자명을 입력해주세요');
            return false;
        }

        if (!formData.password.trim()) {
            onError('비밀번호를 입력해주세요');
            return false;
        }

        if (!isLogin) {
            if (!formData.displayName.trim()) {
                onError('표시명을 입력해주세요');
                return false;
            }
        }

        return true;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!validateForm()) return;

        setLoading(true);

        try {
            if (isLogin) {
                const loginData: LoginRequest = {
                    username: formData.username,
                    password: formData.password,
                };

                const response = await userApi.login(loginData);
                onLogin(response.user);
            } else {
                const registerData: RegisterRequest = {
                    username: formData.username,
                    password: formData.password,
                    displayName: formData.displayName,
                };

                const user = await userApi.register(registerData);
                onLogin(user);
            }
        } catch (error: any) {
            console.error('Authentication error:', error);
            onError(error.response?.data?.message || error.message || '인증에 실패했습니다');
        } finally {
            setLoading(false);
        }
    };

    const toggleMode = () => {
        setIsLogin(!isLogin);
        setFormData({
            username: '',
            password: '',
            displayName: '',
        });
    };

    const containerStyle: React.CSSProperties = {
        width: '100%',
        maxWidth: '400px',
        padding: theme.spacing.xl,
        backgroundColor: theme.colors.white,
        borderRadius: theme.borderRadius.xl,
        boxShadow: theme.shadows.xl,
        border: `1px solid ${theme.colors.borderLight}`,
    };

    const headerStyle: React.CSSProperties = {
        textAlign: 'center',
        marginBottom: theme.spacing.xl,
    };

    const logoStyle: React.CSSProperties = {
        width: '64px',
        height: '64px',
        backgroundColor: theme.colors.primary,
        borderRadius: theme.borderRadius.full,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        margin: `0 auto ${theme.spacing.md}`,
    };

    const titleStyle: React.CSSProperties = {
        fontSize: theme.fontSizes['2xl'],
        fontWeight: theme.fontWeights.bold,
        color: theme.colors.textPrimary,
        margin: `0 0 ${theme.spacing.xs}`,
    };

    const subtitleStyle: React.CSSProperties = {
        fontSize: theme.fontSizes.md,
        color: theme.colors.textSecondary,
        margin: 0,
    };

    const formStyle: React.CSSProperties = {
        display: 'flex',
        flexDirection: 'column',
        gap: theme.spacing.md,
    };

    const inputGroupStyle: React.CSSProperties = {
        position: 'relative',
    };

    const inputIconStyle: React.CSSProperties = {
        position: 'absolute',
        left: theme.spacing.md,
        top: '50%',
        transform: 'translateY(-50%)',
        color: theme.colors.textTertiary,
        zIndex: 1,
    };

    const inputWithIconStyle: React.CSSProperties = {
        paddingLeft: '40px',
    };

    const switchModeStyle: React.CSSProperties = {
        textAlign: 'center',
        marginTop: theme.spacing.lg,
    };

    const switchLinkStyle: React.CSSProperties = {
        color: theme.colors.primary,
        textDecoration: 'none',
        fontWeight: theme.fontWeights.medium,
        cursor: 'pointer',
        fontSize: theme.fontSizes.sm,
    };

    return (
        <div style={containerStyle} className="animate-fadeIn">
            <div style={headerStyle}>
                <div style={logoStyle}>
                    <MessageCircle size={32} color={theme.colors.white} />
                </div>
                <h1 style={titleStyle}>
                    {isLogin ? '로그인' : '회원가입'}
                </h1>
                <p style={subtitleStyle}>
                    {isLogin
                        ? '채팅을 시작하려면 로그인하세요'
                        : '새 계정을 만들어 채팅을 시작하세요'
                    }
                </p>
            </div>

            <form onSubmit={handleSubmit} style={formStyle}>
                <div style={inputGroupStyle}>
                    <UserIcon size={16} style={inputIconStyle} />
                    <Input
                        type="text"
                        value={formData.username}
                        onChange={(value) => handleInputChange('username', value)}
                        placeholder="사용자명"
                        style={inputWithIconStyle}
                        autoFocus
                        maxLength={50}
                    />
                </div>

                {!isLogin && (
                    <>
                        <div style={inputGroupStyle}>
                            <UserIcon size={16} style={inputIconStyle} />
                            <Input
                                type="text"
                                value={formData.displayName}
                                onChange={(value) => handleInputChange('displayName', value)}
                                placeholder="표시명"
                                style={inputWithIconStyle}
                                maxLength={100}
                            />
                        </div>
                    </>
                )}

                <div style={inputGroupStyle}>
                    <Lock size={16} style={inputIconStyle} />
                    <Input
                        type="password"
                        value={formData.password}
                        onChange={(value) => handleInputChange('password', value)}
                        placeholder="비밀번호"
                        style={inputWithIconStyle}
                        maxLength={255}
                    />
                </div>

                <Button
                    type="submit"
                    variant="primary"
                    size="lg"
                    loading={loading}
                    style={{ marginTop: theme.spacing.md }}
                >
                    {isLogin ? (
                        <>
                            <LogIn size={18} />
                            로그인
                        </>
                    ) : (
                        <>
                            <UserPlus size={18} />
                            회원가입
                        </>
                    )}
                </Button>
            </form>

            <div style={switchModeStyle}>
        <span style={{ fontSize: theme.fontSizes.sm, color: theme.colors.textSecondary }}>
          {isLogin ? '계정이 없으신가요? ' : '이미 계정이 있으신가요? '}
        </span>
                <button
                    type="button"
                    onClick={toggleMode}
                    style={switchLinkStyle}
                >
                    {isLogin ? '회원가입' : '로그인'}
                </button>
            </div>
        </div>
    );
};

export default LoginForm;