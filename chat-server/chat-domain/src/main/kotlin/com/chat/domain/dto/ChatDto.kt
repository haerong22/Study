package com.chat.domain.dto

import com.chat.domain.model.ChatRoomType
import com.chat.domain.model.MemberRole
import com.chat.domain.model.MessageType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class ChatRoomDto(
    val id: Long,
    val name: String,
    val description: String?,
    val type: ChatRoomType,
    val imageUrl: String?,
    val isActive: Boolean,
    val maxMembers: Int,
    val memberCount: Int,
    val createdBy: UserDto,
    val createdAt: LocalDateTime,
    val lastMessage: MessageDto?
)

data class CreateChatRoomRequest(
    @field:NotBlank(message = "채팅방 이름은 필수입니다") // {"name": ""}
    @field:Size(min = 1, max = 100, message = "채팅방 이름은 1-100자 사이여야 합니다")
    val name: String,

    val description: String?,

    @field:NotNull(message = "채팅방 타입은 필수입니다") // {"type" :null}
    val type: ChatRoomType,

    val imageUrl: String?,

    val maxMembers: Int = 100,
)

data class MessageDto(
    val id: Long,
    val chatRoomId: Long,
    val sender: UserDto,
    val type: MessageType,
    val content: String?,
    val isEdited: Boolean,
    val isDeleted: Boolean,
    val createdAt: LocalDateTime,
    val editedAt: LocalDateTime?,
    val sequenceNumber: Long = 0 // 메시지 순서 보장을 위한 시퀀스
)

data class SendMessageRequest(
    @field:NotNull(message = "채팅방 ID는 필수입니다")
    val chatRoomId: Long,

    @field:NotNull(message = "메시지 타입은 필수입니다")
    val type: MessageType,

    val content: String?
)

// 커서 기반 페이지네이션을 위한 DTO
data class MessagePageRequest(
    val chatRoomId: Long,
    val cursor: Long? = null, // 마지막 메시지 ID (없으면 최신부터)
    val limit: Int = 50,
    val direction: MessageDirection = MessageDirection.BEFORE // 커서 기준 이전/이후
)

data class MessagePageResponse(
    val messages: List<MessageDto>,
    val nextCursor: Long?, // 다음 페이지를 위한 커서
    val prevCursor: Long?, // 이전 페이지를 위한 커서
    val hasNext: Boolean,
    val hasPrev: Boolean
)

enum class MessageDirection {
    BEFORE, // 커서 이전 메시지들 (과거)
    AFTER   // 커서 이후 메시지들 (최신)
}

data class ChatRoomMemberDto(
    val id: Long,
    val user: UserDto,
    val role: MemberRole,
    val isActive: Boolean,
    val lastReadMessageId: Long?,
    val joinedAt: LocalDateTime,
    val leftAt: LocalDateTime?
)
