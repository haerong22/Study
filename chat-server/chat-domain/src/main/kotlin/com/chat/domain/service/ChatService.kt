package com.chat.domain.service

import com.chat.domain.dto.*
import com.chat.domain.model.ChatRoomType
import com.chat.domain.model.MemberRole
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ChatService {

    // 채팅방 관리
    fun createChatRoom(request: CreateChatRoomRequest, createdBy: Long): ChatRoomDto
    fun getChatRoom(roomId: Long): ChatRoomDto
    fun getChatRooms(userId: Long, pageable: Pageable): Page<ChatRoomDto>
    fun searchChatRooms(query: String, userId: Long): List<ChatRoomDto>

    // 채팅방 멤버 관리
    fun joinChatRoom(roomId: Long, userId: Long)
    fun leaveChatRoom(roomId: Long, userId: Long)
    fun getChatRoomMembers(roomId: Long): List<ChatRoomMemberDto>

    // 메시지 관리
    fun sendMessage(request: SendMessageRequest, senderId: Long): MessageDto
    fun getMessages(roomId: Long, userId: Long, pageable: Pageable): Page<MessageDto>

    // 커서 기반 메시지 페이지네이션 (성능 최적화)
    fun getMessagesByCursor(request: MessagePageRequest, userId: Long): MessagePageResponse
}