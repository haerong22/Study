package com.chat.domain.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(
    name = "chat_room_members",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["chat_room_id", "user_id"])
    ],
    indexes = [
        Index(name = "idx_chat_room_member_user_id", columnList = "user_id"),
        Index(name = "idx_chat_room_member_chat_room_id", columnList = "chat_room_id"),
        Index(name = "idx_chat_room_member_active", columnList = "is_active"),
        Index(name = "idx_chat_room_member_role", columnList = "role")
    ]
)
@EntityListeners(AuditingEntityListener::class)
data class ChatRoomMember(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    val chatRoom: ChatRoom,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val role: MemberRole = MemberRole.MEMBER,

    @Column(nullable = false)
    val isActive: Boolean = true,

    @Column
    val lastReadMessageId: Long? = null,

    @Column(nullable = false)
    val joinedAt: LocalDateTime = LocalDateTime.now(),

    @Column
    val leftAt: LocalDateTime? = null,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
)

enum class MemberRole {
    OWNER,      // 방장
    ADMIN,      // 관리자
    MEMBER      // 일반 멤버
}