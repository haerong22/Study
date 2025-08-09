package com.chat.domain.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(
    name = "chat_rooms",
    indexes = [
        Index(name = "idx_chat_room_created_by", columnList = "created_by"),
        Index(name = "idx_chat_room_type", columnList = "type"),
        Index(name = "idx_chat_room_active", columnList = "is_active")
    ]
)
@EntityListeners(AuditingEntityListener::class)
data class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 100)
    @NotBlank
    val name: String,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val type: ChatRoomType = ChatRoomType.GROUP,

    @Column(length = 500)
    val imageUrl: String? = null,

    @Column(nullable = false)
    val isActive: Boolean = true,

    @Column(nullable = false)
    val maxMembers: Int = 100,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    val createdBy: User,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class ChatRoomType {
    DIRECT,     // 1:1 채팅
    GROUP,      // 그룹 채팅
    CHANNEL     // 채널 (공개)
}