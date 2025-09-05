export const theme = {
    colors: {
        // Primary colors
        primary: '#742DDD',
        primaryHover: '#5D1FBD',
        primaryLight: '#F4F0FF',

        // Secondary colors
        secondary: '#007AFF',
        secondaryHover: '#0056CC',
        secondaryLight: '#E8F4FF',

        // Success colors
        success: '#00C851',
        successHover: '#00A441',
        successLight: '#E8F8ED',

        // Error colors
        error: '#FF3B30',
        errorHover: '#D70015',
        errorLight: '#FFE8E6',

        // Warning colors
        warning: '#FF9500',
        warningHover: '#E6850E',
        warningLight: '#FFF3E0',

        // Neutral colors
        white: '#FFFFFF',
        gray50: '#FAFAFA',
        gray100: '#F5F5F5',
        gray200: '#EEEEEE',
        gray300: '#E0E0E0',
        gray400: '#BDBDBD',
        gray500: '#9E9E9E',
        gray600: '#757575',
        gray700: '#616161',
        gray800: '#424242',
        gray900: '#212121',
        black: '#000000',

        // Chat specific colors
        myMessage: '#742DDD',
        myMessageText: '#FFFFFF',
        otherMessage: '#F5F5F5',
        otherMessageText: '#212121',

        // Status colors
        online: '#00C851',
        offline: '#9E9E9E',
        away: '#FF9500',
        busy: '#FF3B30',

        // Background colors
        background: '#FFFFFF',
        backgroundSecondary: '#FAFAFA',
        backgroundTertiary: '#F5F5F5',

        // Border colors
        border: '#E0E0E0',
        borderLight: '#F0F0F0',
        borderDark: '#BDBDBD',

        // Text colors
        textPrimary: '#212121',
        textSecondary: '#757575',
        textTertiary: '#9E9E9E',
        textInverse: '#FFFFFF',
    },

    fonts: {
        primary: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif',
        mono: 'SFMono-Regular, Consolas, "Liberation Mono", Menlo, monospace',
    },

    fontSizes: {
        xs: '12px',
        sm: '14px',
        md: '16px',
        lg: '18px',
        xl: '20px',
        '2xl': '24px',
        '3xl': '32px',
        '4xl': '40px',
    },

    fontWeights: {
        normal: 400,
        medium: 500,
        semibold: 600,
        bold: 700,
    },

    lineHeights: {
        tight: 1.2,
        normal: 1.5,
        relaxed: 1.6,
    },

    spacing: {
        xs: '4px',
        sm: '8px',
        md: '16px',
        lg: '24px',
        xl: '32px',
        '2xl': '48px',
        '3xl': '64px',
    },

    borderRadius: {
        none: '0',
        sm: '4px',
        md: '8px',
        lg: '12px',
        xl: '16px',
        full: '9999px',
    },

    shadows: {
        sm: '0 1px 2px rgba(0, 0, 0, 0.05)',
        md: '0 4px 6px rgba(0, 0, 0, 0.07)',
        lg: '0 10px 15px rgba(0, 0, 0, 0.1)',
        xl: '0 20px 25px rgba(0, 0, 0, 0.1)',
    },

    transitions: {
        fast: '0.15s ease-in-out',
        normal: '0.2s ease-in-out',
        slow: '0.3s ease-in-out',
    },

    breakpoints: {
        sm: '640px',
        md: '768px',
        lg: '1024px',
        xl: '1280px',
    },

    zIndex: {
        dropdown: 1000,
        sticky: 1020,
        fixed: 1030,
        modal: 1040,
        popover: 1050,
        tooltip: 1060,
    },
} as const;

export type Theme = typeof theme;