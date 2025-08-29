import axios, { AxiosResponse } from 'axios';
import {
    User,
    LoginRequest,
    RegisterRequest,
    LoginResponse,
    ChatRoom,
    CreateChatRoomRequest,
    Message,
    PageResponse,
    CursorResponse,
    ChatRoomMember,
    SearchResult,
    SearchQuery,
    UserSettings,
} from '../types';

// Axios 기본 설정
const api = axios.create({
    baseURL: 'http://localhost/api',
    timeout: 30000,
    headers: {
        'Content-Type': 'application/json',
    },
});

// 요청/응답 인터셉터
api.interceptors.request.use(
    (config) => {
        // console.log(`[API Request] ${config.method?.toUpperCase()} ${config.url}`);
        return config;
    },
    (error) => {
        // console.error('[API Request Error]', error);
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response) => {
        // console.log(`[API Response] ${response.status} ${response.config.url}`);
        return response;
    },
    (error) => {
        console.error('[API Response Error]', error.response?.data || error.message);

        // 에러 타입별 처리
        if (error.response?.status === 401) {
            // 인증 오류 처리
            console.warn('Authentication error - redirecting to login');
        } else if (error.response?.status === 403) {
            // 권한 오류 처리
            console.warn('Authorization error - access denied');
        } else if (error.response?.status >= 500) {
            // 서버 오류 처리
            console.error('Server error - please try again later');
        }

        return Promise.reject(error);
    }
);

// 사용자 관련 API
export const userApi = {
    // 사용자 등록
    register: async (data: RegisterRequest): Promise<User> => {
        const response: AxiosResponse<User> = await api.post('/users/register', data);
        return response.data;
    },

    // 사용자 로그인
    login: async (data: LoginRequest): Promise<LoginResponse> => {
        const response: AxiosResponse<User> = await api.post('/users/login', data);
        return { user: response.data };
    },

    // 사용자 정보 조회
    getUser: async (userId: number): Promise<User> => {
        const response: AxiosResponse<User> = await api.get(`/users/${userId}`);
        return response.data;
    },

    // 사용자 검색
    searchUsers: async (query: string): Promise<User[]> => {
        const response: AxiosResponse<User[]> = await api.get(`/users/search?q=${encodeURIComponent(query)}`);
        return response.data;
    },

    // 사용자 프로필 업데이트
    updateProfile: async (userId: number, data: Partial<User>): Promise<User> => {
        const response: AxiosResponse<User> = await api.put(`/users/${userId}`, data);
        return response.data;
    },

    // 사용자 상태 업데이트
    updateStatus: async (userId: number, status: string): Promise<void> => {
        await api.patch(`/users/${userId}/status`, { status });
    },

    // 사용자 설정 조회
    getSettings: async (userId: number): Promise<UserSettings> => {
        const response: AxiosResponse<UserSettings> = await api.get(`/users/${userId}/settings`);
        return response.data;
    },

    // 사용자 설정 업데이트
    updateSettings: async (userId: number, settings: UserSettings): Promise<UserSettings> => {
        const response: AxiosResponse<UserSettings> = await api.put(`/users/${userId}/settings`, settings);
        return response.data;
    },
};

// 채팅방 관련 API
export const chatRoomApi = {
    // 채팅방 생성
    create: async (data: CreateChatRoomRequest, createdBy: number): Promise<ChatRoom> => {
        const response: AxiosResponse<ChatRoom> = await api.post(
            `/chat-rooms?createdBy=${createdBy}`,
            data
        );
        return response.data;
    },

    // 채팅방 목록 조회 (페이지네이션)
    getChatRooms: async (userId: number, page = 0, size = 20): Promise<PageResponse<ChatRoom>> => {
        const response: AxiosResponse<PageResponse<ChatRoom>> = await api.get(
            `/chat-rooms?userId=${userId}&page=${page}&size=${size}&sort=createdAt,desc`
        );
        return response.data;
    },

    // 채팅방 상세 조회
    getChatRoom: async (chatRoomId: number): Promise<ChatRoom> => {
        const response: AxiosResponse<ChatRoom> = await api.get(`/chat-rooms/${chatRoomId}`);
        return response.data;
    },

    // 채팅방 멤버 조회
    getMembers: async (chatRoomId: number): Promise<ChatRoomMember[]> => {
        const response: AxiosResponse<ChatRoomMember[]> = await api.get(
            `/chat-rooms/${chatRoomId}/members`
        );
        return response.data;
    },

    // 채팅방 참여
    join: async (chatRoomId: number, userId: number): Promise<void> => {
        await api.post(`/chat-rooms/${chatRoomId}/members`, { userId });
    },

    // 채팅방 나가기
    leave: async (chatRoomId: number, userId: number): Promise<void> => {
        await api.delete(`/chat-rooms/${chatRoomId}/members/me?userId=${userId}`);
    },

    // 채팅방 정보 업데이트
    update: async (chatRoomId: number, data: Partial<ChatRoom>): Promise<ChatRoom> => {
        const response: AxiosResponse<ChatRoom> = await api.put(`/chat-rooms/${chatRoomId}`, data);
        return response.data;
    },

    // 채팅방 삭제
    delete: async (chatRoomId: number): Promise<void> => {
        await api.delete(`/chat-rooms/${chatRoomId}`);
    },

    // 채팅방 검색
    search: async (query: string, userId: number): Promise<ChatRoom[]> => {
        const response: AxiosResponse<ChatRoom[]> = await api.get(
            `/chat-rooms/search?q=${encodeURIComponent(query)}&userId=${userId}`
        );
        return response.data;
    },
};

// 메시지 관련 API
export const messageApi = {
    // 메시지 목록 조회 (페이지네이션)
    getMessages: async (
        chatRoomId: number,
        userId: number,
        page = 0,
        size = 50
    ): Promise<PageResponse<Message>> => {
        const response: AxiosResponse<PageResponse<Message>> = await api.get(
            `/chat-rooms/${chatRoomId}/messages?userId=${userId}&page=${page}&size=${size}&sort=sequenceNumber,asc`
        );
        return response.data;
    },

    // 커서 기반 메시지 조회 (성능 최적화)
    getMessagesByCursor: async (
        chatRoomId: number,
        userId: number,
        cursor?: string,
        limit = 50,
        direction: 'BEFORE' | 'AFTER' = 'BEFORE'
    ): Promise<CursorResponse<Message>> => {
        const params = new URLSearchParams({
            userId: userId.toString(),
            limit: limit.toString(),
            direction,
        });

        if (cursor) {
            params.append('cursor', cursor);
        }

        const response: AxiosResponse<CursorResponse<Message>> = await api.get(
            `/chat-rooms/${chatRoomId}/messages/cursor?${params}`
        );
        return response.data;
    },

    // 메시지 검색
    searchMessages: async (
        chatRoomId: number,
        query: SearchQuery
    ): Promise<SearchResult> => {
        const params = new URLSearchParams();
        params.append('chatRoomId', chatRoomId.toString());
        params.append('query', query.query);

        if (query.messageType) params.append('messageType', query.messageType);
        if (query.senderId) params.append('senderId', query.senderId.toString());
        if (query.dateFrom) params.append('dateFrom', query.dateFrom);
        if (query.dateTo) params.append('dateTo', query.dateTo);

        const response: AxiosResponse<SearchResult> = await api.get(`/messages/search?${params}`);
        return response.data;
    },

    // 메시지 삭제
    deleteMessage: async (chatRoomId: number, messageId: number): Promise<void> => {
        await api.delete(`/chat-rooms/${chatRoomId}/messages/${messageId}`);
    },

    // 메시지 수정
    editMessage: async (chatRoomId: number, messageId: number, content: string): Promise<Message> => {
        const response: AxiosResponse<Message> = await api.put(
            `/chat-rooms/${chatRoomId}/messages/${messageId}`,
            { content }
        );
        return response.data;
    },

    // 메시지 읽음 처리
    markAsRead: async (chatRoomId: number, userId: number, messageId: number): Promise<void> => {
        await api.post(`/chat-rooms/${chatRoomId}/messages/${messageId}/read`, { userId });
    },
};

// 파일 업로드 API
export const fileApi = {
    // 파일 업로드
    upload: async (file: File, onProgress?: (progress: number) => void): Promise<string> => {
        const formData = new FormData();
        formData.append('file', file);

        const response: AxiosResponse<{ url: string }> = await api.post('/files/upload', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
            onUploadProgress: (progressEvent) => {
                if (onProgress && progressEvent.total) {
                    const progress = Math.round((progressEvent.loaded * 100) / progressEvent.total);
                    onProgress(progress);
                }
            },
        });

        return response.data.url;
    },

    // 파일 삭제
    delete: async (fileUrl: string): Promise<void> => {
        await api.delete('/files', { data: { url: fileUrl } });
    },
};

// 헬스체크 API
export const healthApi = {
    check: async (): Promise<{ status: string; components: any }> => {
        const response = await api.get('/actuator/health');
        return response.data;
    },

    // 서버 정보 조회
    info: async (): Promise<any> => {
        const response = await api.get('/actuator/info');
        return response.data;
    },

    // 메트릭 조회
    metrics: async (): Promise<any> => {
        const response = await api.get('/actuator/metrics');
        return response.data;
    },
};

// 알림 API
export const notificationApi = {
    // 알림 목록 조회
    getNotifications: async (userId: number): Promise<any[]> => {
        const response = await api.get(`/notifications?userId=${userId}`);
        return response.data;
    },

    // 알림 읽음 처리
    markAsRead: async (notificationId: string): Promise<void> => {
        await api.patch(`/notifications/${notificationId}/read`);
    },

    // 알림 설정 업데이트
    updateSettings: async (userId: number, settings: any): Promise<void> => {
        await api.put(`/notifications/settings?userId=${userId}`, settings);
    },
};

export default api;