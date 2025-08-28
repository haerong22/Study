import React from 'react';

// 사용자 관련 타입
export interface User {
    id: number;
    username: string;
    displayName: string;
    profileImageUrl?: string;
    status?: UserStatus;
    isActive: boolean;
    lastSeenAt?: string;
    createdAt: string;
}

export type UserStatus = 'online' | 'offline' | 'away' | 'busy';

export interface LoginRequest {
    username: string;
    password: string;
}

export interface RegisterRequest {
    username: string;
    password: string;
    displayName: string;
}

export interface LoginResponse {
    user: User;
    token?: string;
}

// 채팅방 관련 타입
export interface ChatRoom {
    id: number;
    name: string;
    description?: string;
    type: ChatRoomType;
    imageUrl?: string;
    isActive: boolean;
    maxMembers: number;
    memberCount: number;
    createdBy: User;
    createdAt: string;
    lastMessage?: Message;
    unreadCount?: number;
}

export type ChatRoomType = 'GROUP' | 'DIRECT' | 'CHANNEL';

export interface CreateChatRoomRequest {
    name: string;
    description?: string;
    type: ChatRoomType;
    maxMembers: number;
}

export interface ChatRoomMember {
    id: number;
    user: User;
    role: MemberRole;
    joinedAt: string;
    isActive: boolean;
    lastReadMessageId?: number;
    leftAt?: string;
}

export type MemberRole = 'OWNER' | 'ADMIN' | 'MEMBER';

// 메시지 관련 타입
export interface Message {
    id: number;
    chatRoomId: number;
    sender?: User;
    type: MessageType;
    content: string;
    sequenceNumber: number;
    isEdited: boolean;
    isDeleted: boolean;
    createdAt: string;
    editedAt?: string;
    fileName?: string;
    fileUrl?: string;
    fileSize?: number;
    threadMessageId?: number;
    reactions?: MessageReaction[];
    mentions?: User[];
}

export type MessageType = 'TEXT' | 'IMAGE' | 'FILE' | 'SYSTEM';

export interface MessageReaction {
    id: number;
    emoji: string;
    users: User[];
    count: number;
}

export interface TypingIndicator {
    userId: number;
    userName: string;
    chatRoomId: number;
    isTyping: boolean;
    timestamp: number;
}

// WebSocket 메시지 타입
export interface WebSocketMessage {
    type: WebSocketMessageType;
    chatRoomId?: number;
    messageType?: MessageType;
    content?: string;
    isTyping?: boolean;
    message?: string;
    senderName?: string;
    userName?: string;
    userId?: number;
    timestamp?: number;
}

export type WebSocketMessageType =
    | 'SEND_MESSAGE'
    | 'TYPING_INDICATOR'
    | 'CHAT_MESSAGE'
    | 'USER_JOINED'
    | 'USER_LEFT'
    | 'USER_STATUS_CHANGED'
    | 'MESSAGE_REACTION'
    | 'ERROR';

// API 응답 타입
export interface ApiResponse<T> {
    data?: T;
    message?: string;
    timestamp?: string;
    status?: number;
    error?: string;
}

export interface PageResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    first: boolean;
    last: boolean;
    empty: boolean;
}

export interface CursorResponse<T> {
    messages: T[];
    nextCursor?: string;
    prevCursor?: string;
    hasNext: boolean;
    hasPrev: boolean;
}

// UI 상태 타입
export interface AppState {
    currentUser?: User;
    selectedChatRoom?: ChatRoom;
    chatRooms: ChatRoom[];
    messages: Message[];
    isConnected: boolean;
    isLoading: boolean;
    error?: string;
    typingUsers: TypingIndicator[];
}

export interface UIState {
    sidebarOpen: boolean;
    userListOpen: boolean;
    emojiPickerOpen: boolean;
    searchOpen: boolean;
    settingsOpen: boolean;
    theme: 'light' | 'dark';
}

// 파일 업로드 타입
export interface FileUpload {
    file: File;
    id: string;
    progress: number;
    status: 'pending' | 'uploading' | 'completed' | 'error';
    url?: string;
    error?: string;
}

// 검색 관련 타입
export interface SearchResult {
    messages: Message[];
    chatRooms: ChatRoom[];
    users: User[];
    totalCount: number;
}

export interface SearchQuery {
    query: string;
    chatRoomId?: number;
    messageType?: MessageType;
    senderId?: number;
    dateFrom?: string;
    dateTo?: string;
}

// 알림 타입
export interface Notification {
    id: string;
    type: 'message' | 'mention' | 'system' | 'error';
    title: string;
    message: string;
    chatRoomId?: number;
    userId?: number;
    timestamp: number;
    read: boolean;
    actions?: NotificationAction[];
}

export interface NotificationAction {
    label: string;
    action: () => void;
    style?: 'primary' | 'secondary' | 'danger';
}

// 설정 타입
export interface UserSettings {
    notifications: {
        desktop: boolean;
        sound: boolean;
        mentions: boolean;
        allMessages: boolean;
    };
    appearance: {
        theme: 'light' | 'dark' | 'auto';
        fontSize: 'small' | 'medium' | 'large';
        messagePreview: boolean;
    };
    privacy: {
        lastSeen: boolean;
        readReceipts: boolean;
        typing: boolean;
    };
}

// 에러 타입
export interface AppError {
    code: string;
    message: string;
    details?: any;
    timestamp: number;
}

// 컴포넌트 Props 타입
export interface BaseComponentProps {
    className?: string;
    style?: React.CSSProperties;
    children?: React.ReactNode;
}

export interface ButtonProps extends BaseComponentProps {
    variant?: 'primary' | 'secondary' | 'ghost' | 'danger';
    size?: 'sm' | 'md' | 'lg';
    disabled?: boolean;
    loading?: boolean;
    onClick?: () => void;
    type?: 'button' | 'submit' | 'reset';
}

export interface InputProps extends BaseComponentProps {
    value: string;
    onChange: (value: string) => void;
    placeholder?: string;
    disabled?: boolean;
    error?: string;
    type?: 'text' | 'email' | 'password' | 'search' | 'number';
    maxLength?: number;
    autoFocus?: boolean;
}