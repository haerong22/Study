package com.chat.persistence.service

import com.chat.domain.dto.ChatMessage
import com.chat.domain.dto.ChatRoomDto
import com.chat.domain.dto.ChatRoomMemberDto
import com.chat.domain.dto.CreateChatRoomRequest
import com.chat.domain.dto.MessageDirection
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
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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
) : ChatService {
    private val log = LoggerFactory.getLogger(javaClass)

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

    @Cacheable(value = ["chatRooms"], key = "#roomId")
    override fun getChatRoom(roomId: Long): ChatRoomDto {
        val chatRoom = chatRoomRepository.findById(roomId)
            .orElseThrow { IllegalArgumentException("채팅방을 찾을 수 없습니다: $roomId") }
        return chatRoomToDto(chatRoom)
    }

    override fun getChatRooms(userId: Long, pageable: Pageable): Page<ChatRoomDto> {
        return chatRoomRepository.findUserChatRooms(userId, pageable)
            .map { chatRoomToDto(it) }
    }

    override fun searchChatRooms(query: String, userId: Long): List<ChatRoomDto> {
        val chatRooms = if (query.isBlank()) {
            chatRoomRepository.findByIsActiveTrueOrderByCreatedAtDesc()
        } else {
            chatRoomRepository.findByNameContainingIgnoreCaseAndIsActiveTrueOrderByCreatedAtDesc(query)
        }

        return chatRooms.map { chatRoomToDto(it) }
    }

    @Caching(
        evict = [
            CacheEvict(value = ["chatRoomMembers"], key = "#roomId"),
            CacheEvict(value = ["chatRooms"], key = "#roomId")
        ]
    )
    override fun joinChatRoom(roomId: Long, userId: Long) {
        // 채팅방 확인
        val chatRoom = chatRoomRepository.findById(roomId)
            .orElseThrow { IllegalArgumentException("채팅방을 찾을 수 없습니다: $roomId") }

        // 사용자 확인
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다: $userId") }

        // 이미 참여중인지 확인
        if (chatRoomMemberRepository.existsByChatRoomIdAndUserIdAndIsActiveTrue(roomId, userId)) {
            throw IllegalStateException("이미 참여한 채팅방입니다")
        }

        val currentMemberCount = chatRoomMemberRepository.countActiveMembersInRoom(roomId)
        if (currentMemberCount >= chatRoom.maxMembers) {
            throw IllegalStateException("채팅방이 가득 찼습니다")
        }

        val member = ChatRoomMember(
            chatRoom = chatRoom,
            user = user,
            role = MemberRole.MEMBER
        )
        chatRoomMemberRepository.save(member)

        if (webSocketSessionManager.isUserOnlineLocally(userId)) {
            webSocketSessionManager.joinRoom(userId, roomId)
        }
    }

    @Caching(
        evict = [
            CacheEvict(value = ["chatRoomMembers"], key = "#roomId"),
            CacheEvict(value = ["chatRooms"], key = "#roomId")
        ]
    )
    override fun leaveChatRoom(roomId: Long, userId: Long) {
        chatRoomMemberRepository.leaveChatRoom(roomId, userId)
    }

    @Cacheable(value = ["chatRoomMembers"], key = "#roomId")
    override fun getChatRoomMembers(roomId: Long): List<ChatRoomMemberDto> {
        return chatRoomMemberRepository.findByChatRoomIdAndIsActiveTrue(roomId)
            .map { memberToDto(it) }
    }

    override fun sendMessage(request: SendMessageRequest, senderId: Long): MessageDto {
        val chatRoom = chatRoomRepository.findById(request.chatRoomId)
            .orElseThrow { IllegalArgumentException("채팅방을 찾을 수 없습니다: ${request.chatRoomId}") }

        val sender = userRepository.findById(senderId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다: $senderId") }

        chatRoomMemberRepository.findByChatRoomIdAndUserIdAndIsActiveTrue(request.chatRoomId, senderId)
            ?: IllegalArgumentException("채팅방에 참여하지 않은 사용자입니다.")

        val sequenceNumber = messageSequenceService.getNextSequence(request.chatRoomId)

        val message = Message(
            content = request.content,
            type = request.type,
            chatRoom = chatRoom,
            sender = sender,
            sequenceNumber = sequenceNumber
        )
        val savedMessage = messageRepository.save(message)

        val chatMessage = ChatMessage(
            id = savedMessage.id,
            content = savedMessage.content ?: "",
            type = savedMessage.type,
            chatRoomId = savedMessage.chatRoom.id,
            senderId = savedMessage.sender.id,
            senderName = savedMessage.sender.displayName,
            sequenceNumber = savedMessage.sequenceNumber,
            timestamp = savedMessage.createdAt
        )

        // 1. 로컬 세션에 즉시 전송 (실시간 응답성 보장)
        webSocketSessionManager.sendMessageToLocalRoom(request.chatRoomId, chatMessage)

        // 2. 다른 서버 인스턴스에 브로드캐스트 (자신을 제외)
        try {
            redisMessageBroker.broadcastToRoom(
                roomId = request.chatRoomId,
                message = chatMessage,
                excludeServerId = redisMessageBroker.getServerId()
            )
        } catch (e: Exception) {
            log.error("Failed to broadcast message via Redis: ${e.message}", e)
        }

        return messageToDto(savedMessage)
    }

    override fun getMessages(roomId: Long, userId: Long, pageable: Pageable): Page<MessageDto> {
        if (!chatRoomMemberRepository.existsByChatRoomIdAndUserIdAndIsActiveTrue(roomId, userId)) {
            throw IllegalArgumentException("채팅방 멤버가 아닙니다")
        }

        return messageRepository.findByChatRoomId(roomId, pageable)
            .map { messageToDto(it) }
    }

    override fun getMessagesByCursor(request: MessagePageRequest, userId: Long): MessagePageResponse {
        /*
            SELECT *
            FROM chat_room_member
            WHERE chat_room_id = :chatRoomId AND is_active = true
            ORDER BY id
            LIMIT 10 OFFSET 10;

            SELECT *
            FROM chat_room_member
            WHERE chat_room_id = :chatRoomId AND is_active = true AND id > :cursor
            ORDER BY id ASC
            LIMIT 10;
         */

        if (!chatRoomMemberRepository.existsByChatRoomIdAndUserIdAndIsActiveTrue(request.chatRoomId, userId)) {
            throw IllegalArgumentException("채팅방 멤버가 아닙니다")
        }

        val pageable = PageRequest.of(0, request.limit)
        val cursor = request.cursor // 로컬 변수로 복사하여 스마트 캐스트 가능하게 함

        val messages = when {
            cursor == null -> {
                // 커서가 없으면 최신 메시지부터
                messageRepository.findLatestMessages(request.chatRoomId, pageable)
            }

            request.direction == MessageDirection.BEFORE -> {
                // 커서 이전 메시지들 (과거 방향)
                messageRepository.findMessagesBefore(request.chatRoomId, cursor, pageable)
            }

            else -> {
                // 커서 이후 메시지들 (최신 방향)
                messageRepository.findMessagesAfter(request.chatRoomId, cursor, pageable)
                    .reversed() // 시간순 정렬로 변경
            }
        }

        val messageDtos = messages.map { messageToDto(it) }

        // 다음/이전 커서 계산
        val nextCursor = if (messageDtos.isNotEmpty()) messageDtos.last().id else null
        val prevCursor = if (messageDtos.isNotEmpty()) messageDtos.first().id else null

        // 추가 데이터 존재 여부 확인
        val hasNext = messages.size == request.limit
        val hasPrev = cursor != null

        return MessagePageResponse(
            messages = messageDtos,
            nextCursor = nextCursor,
            prevCursor = prevCursor,
            hasNext = hasNext,
            hasPrev = hasPrev
        )
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

    private fun memberToDto(member: ChatRoomMember): ChatRoomMemberDto {
        return ChatRoomMemberDto(
            id = member.id,
            user = userToDto(member.user),
            role = member.role,
            isActive = member.isActive,
            lastReadMessageId = member.lastReadMessageId,
            joinedAt = member.joinedAt,
            leftAt = member.leftAt
        )
    }
}