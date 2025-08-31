import React from 'react';
import { ButtonProps } from '../../types/index';
import { theme } from '../../styles/theme.ts';

const Button: React.FC<ButtonProps> = ({
                                           children,
                                           variant = 'primary',
                                           size = 'md',
                                           disabled = false,
                                           loading = false,
                                           onClick,
                                           type = 'button',
                                           className = '',
                                           style,
                                           ...props
                                       }) => {
    const getVariantStyles = () => {
        switch (variant) {
            case 'primary':
                return {
                    backgroundColor: disabled ? theme.colors.gray300 : theme.colors.primary,
                    color: theme.colors.white,
                    border: 'none',
                    '&:hover': {
                        backgroundColor: disabled ? theme.colors.gray300 : theme.colors.primaryHover,
                    },
                };
            case 'secondary':
                return {
                    backgroundColor: disabled ? theme.colors.gray100 : theme.colors.secondary,
                    color: theme.colors.white,
                    border: 'none',
                    '&:hover': {
                        backgroundColor: disabled ? theme.colors.gray100 : theme.colors.secondaryHover,
                    },
                };
            case 'ghost':
                return {
                    backgroundColor: 'transparent',
                    color: disabled ? theme.colors.gray400 : theme.colors.primary,
                    border: `1px solid ${disabled ? theme.colors.gray300 : theme.colors.primary}`,
                    '&:hover': {
                        backgroundColor: disabled ? 'transparent' : theme.colors.primaryLight,
                    },
                };
            case 'danger':
                return {
                    backgroundColor: disabled ? theme.colors.gray300 : theme.colors.error,
                    color: theme.colors.white,
                    border: 'none',
                    '&:hover': {
                        backgroundColor: disabled ? theme.colors.gray300 : theme.colors.errorHover,
                    },
                };
            default:
                return {};
        }
    };

    const getSizeStyles = () => {
        switch (size) {
            case 'sm':
                return {
                    padding: `${theme.spacing.xs} ${theme.spacing.sm}`,
                    fontSize: theme.fontSizes.sm,
                    minHeight: '32px',
                };
            case 'md':
                return {
                    padding: `${theme.spacing.sm} ${theme.spacing.md}`,
                    fontSize: theme.fontSizes.md,
                    minHeight: '40px',
                };
            case 'lg':
                return {
                    padding: `${theme.spacing.md} ${theme.spacing.lg}`,
                    fontSize: theme.fontSizes.lg,
                    minHeight: '48px',
                };
            default:
                return {};
        }
    };

    const baseStyles: React.CSSProperties = {
        display: 'inline-flex',
        alignItems: 'center',
        justifyContent: 'center',
        gap: theme.spacing.xs,
        borderRadius: theme.borderRadius.md,
        fontWeight: theme.fontWeights.medium,
        fontFamily: theme.fonts.primary,
        cursor: disabled || loading ? 'not-allowed' : 'pointer',
        transition: theme.transitions.normal,
        outline: 'none',
        userSelect: 'none',
        whiteSpace: 'nowrap',
        opacity: disabled ? 0.6 : 1,
        ...getSizeStyles(),
        ...getVariantStyles(),
        ...style,
    };

    const handleClick = () => {
        if (!disabled && !loading && onClick) {
            onClick();
        }
    };

    return (
        <button
            type={type}
            className={`btn btn-${variant} btn-${size} ${className}`}
            style={baseStyles}
            onClick={handleClick}
            disabled={disabled || loading}
            {...props}
        >
            {loading && (
                <div
                    style={{
                        width: '16px',
                        height: '16px',
                        border: '2px solid transparent',
                        borderTop: '2px solid currentColor',
                        borderRadius: '50%',
                        animation: 'spin 1s linear infinite',
                    }}
                />
            )}
            {children}
        </button>
    );
};

export default Button;