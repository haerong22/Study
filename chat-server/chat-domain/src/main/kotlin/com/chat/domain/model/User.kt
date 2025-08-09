package com.chat.domain.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "app_users")
@EntityListeners(AuditingEntityListener::class)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank
    val username: String,

    @Column(nullable = false, length = 255)
    val password: String,

    @Column(nullable = false, length = 100)
    val displayName: String,

    @Column(length = 500)
    val profileImageUrl: String? = null,

    @Column(length = 50)
    val status: String? = null,

    @Column(nullable = false)
    val isActive: Boolean = true,

    @Column
    val lastSeenAt: LocalDateTime? = null,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)