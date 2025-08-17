package com.chat.persistence.service

import com.chat.domain.dto.ChatRoomDto
import com.chat.domain.dto.ChatRoomMemberDto
import com.chat.domain.dto.CreateChatRoomRequest
import com.chat.domain.dto.MessageDto
import com.chat.domain.dto.MessagePageRequest
import com.chat.domain.dto.MessagePageResponse
import com.chat.domain.dto.SendMessageRequest
import com.chat.domain.dto.UserDto
import com.chat.domain.model.ChatRoom
import com.chat.domain.model.ChatRoomMember
import com.chat.domain.model.MemberRole
import com.chat.domain.model.Message
import com.chat.domain.model.User
import com.chat.domain.service.ChatService
import com.chat.persistence.redis.RedisMessageBroker
import com.chat.persistence.repository.ChatRoomMemberRepository
import com.chat.persistence.repository.ChatRoomRepository
import com.chat.persistence.repository.MessageRepository
import com.chat.persistence.repository.UserRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChatServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val messageRepository: MessageRepository,
    private val chatRoomMemberRepository: ChatRoomMemberRepository,
    private val userRepository: UserRepository,
    private val redisMessageBroker: RedisMessageBroker,
    private val messageSequenceService: MessageSequenceService,
    private val webSocketSessionManager: WebSocketSessionManager,
): ChatService {

    @CacheEvict(value = ["chatRooms"], allEntries = true)
    override fun createChatRoom(request: CreateChatRoomRequest, createdBy: Long): ChatRoomDto {
        val creator = userRepository.findById(createdBy)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다: $createdBy") }

        val chatRoom = ChatRoom(
            name = request.name,
            description = request.description,
            type = request.type,
            imageUrl = request.imageUrl,
            maxMembers = request.maxMembers,
            createdBy = creator
        )

        val savedRoom = chatRoomRepository.save(chatRoom)

        val ownerMember = ChatRoomMember(
            chatRoom = savedRoom,
            user = creator,
            role = MemberRole.OWNER
        )
        chatRoomMemberRepository.save(ownerMember)

        // 생성자 세션 갱신
        if (webSocketSessionManager.isUserOnlineLocally(creator.id)) {
            webSocketSessionManager.joinRoom(creator.id, savedRoom.id)
        }

        return chatRoomToDto(savedRoom)
    }

    override fun getChatRoom(roomId: Long): ChatRoomDto {
        TODO("Not yet implemented")
    }

    override fun getChatRooms(userId: Long, pageable: Pageable): Page<ChatRoomDto> {
        TODO("Not yet implemented")
    }

    override fun searchChatRooms(query: String, userId: Long): List<ChatRoomDto> {
        TODO("Not yet implemented")
    }

    override fun joinChatRoom(roomId: Long, userId: Long) {
        TODO("Not yet implemented")
    }

    override fun leaveChatRoom(roomId: Long, userId: Long) {
        TODO("Not yet implemented")
    }

    override fun getChatRoomMembers(roomId: Long): List<ChatRoomMemberDto> {
        TODO("Not yet implemented")
    }

    override fun sendMessage(request: SendMessageRequest, senderId: Long): MessageDto {
        TODO("Not yet implemented")
    }

    override fun getMessages(roomId: Long, userId: Long, pageable: Pageable): Page<MessageDto> {
        TODO("Not yet implemented")
    }

    override fun getMessagesByCursor(request: MessagePageRequest, userId: Long): MessagePageResponse {
        TODO("Not yet implemented")
    }

    private fun chatRoomToDto(chatRoom: ChatRoom): ChatRoomDto {
        val memberCount = chatRoomMemberRepository.countActiveMembersInRoom(chatRoom.id).toInt()
        val lastMessage = messageRepository.findLatestMessage(chatRoom.id)?.let { messageToDto(it) }

        return ChatRoomDto(
            id = chatRoom.id,
            name = chatRoom.name,
            description = chatRoom.description,
            type = chatRoom.type,
            imageUrl = chatRoom.imageUrl,
            isActive = chatRoom.isActive,
            maxMembers = chatRoom.maxMembers,
            memberCount = memberCount,
            createdBy = userToDto(chatRoom.createdBy),
            createdAt = chatRoom.createdAt,
            lastMessage = lastMessage
        )
    }

    private fun messageToDto(message: Message): MessageDto {
        return MessageDto(
            id = message.id,
            chatRoomId = message.chatRoom.id,
            sender = userToDto(message.sender),
            type = message.type,
            content = message.content,
            isEdited = message.isEdited,
            isDeleted = message.isDeleted,
            createdAt = message.createdAt,
            editedAt = message.editedAt,
            sequenceNumber = message.sequenceNumber
        )
    }

    private fun userToDto(user: User): UserDto {
        return UserDto(
            id = user.id,
            username = user.username,
            displayName = user.displayName,
            profileImageUrl = user.profileImageUrl,
            status = user.status,
            isActive = user.isActive,
            lastSeenAt = user.lastSeenAt,
            createdAt = user.createdAt
        )
    }
}