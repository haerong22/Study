import React, {forwardRef} from 'react';
import {InputProps} from '../../types/index';
import {theme} from '../../styles/theme.ts';

const Input = forwardRef<HTMLInputElement, InputProps>(({
                                                            value,
                                                            onChange,
                                                            placeholder,
                                                            disabled = false,
                                                            error,
                                                            type = 'text',
                                                            maxLength,
                                                            autoFocus = false,
                                                            className = '',
                                                            style,
                                                            ...props
                                                        }, ref) => {
    const baseStyles: React.CSSProperties = {
        width: '100%',
        padding: `${theme.spacing.sm} ${theme.spacing.md}`,
        fontSize: theme.fontSizes.md,
        fontFamily: theme.fonts.primary,
        backgroundColor: disabled ? theme.colors.gray100 : theme.colors.white,
        border: `1px solid ${error ? theme.colors.error : theme.colors.border}`,
        borderRadius: theme.borderRadius.md,
        outline: 'none',
        transition: theme.transitions.normal,
        color: disabled ? theme.colors.gray500 : theme.colors.textPrimary,
        ...style,
    };

    const focusStyles: React.CSSProperties = {
        borderColor: error ? theme.colors.error : theme.colors.primary,
        boxShadow: `0 0 0 2px ${error ? theme.colors.errorLight : theme.colors.primaryLight}`,
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        onChange(e.target.value);
    };

    const handleFocus = (e: React.FocusEvent<HTMLInputElement>) => {
        Object.assign(e.target.style, focusStyles);
    };

    const handleBlur = (e: React.FocusEvent<HTMLInputElement>) => {
        e.target.style.borderColor = error ? theme.colors.error : theme.colors.border;
        e.target.style.boxShadow = 'none';
    };

    return (
        <div style={{width: '100%'}}>
            <input
                ref={ref}
                type={type}
                value={value}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
                placeholder={placeholder}
                disabled={disabled}
                maxLength={maxLength}
                autoFocus={autoFocus}
                className={`input ${error ? 'input-error' : ''} ${className}`}
                style={baseStyles}
                {...props}
            />
            {error && (
                <div
                    style={{
                        marginTop: theme.spacing.xs,
                        fontSize: theme.fontSizes.sm,
                        color: theme.colors.error,
                    }}
                >
                    {error}
                </div>
            )}
        </div>
    );
});

Input.displayName = 'Input';

export default Input;