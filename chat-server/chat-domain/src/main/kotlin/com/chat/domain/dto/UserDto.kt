package com.chat.domain.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class UserDto(
    val id: Long,
    val username: String,
    val displayName: String,
    val profileImageUrl: String?,
    val status: String?,
    val isActive: Boolean,
    val lastSeenAt: LocalDateTime?,
    val createdAt: LocalDateTime
)

data class CreateUserRequest(
    @field:NotBlank(message = "사용자명은 필수입니다")
    @field:Size(min = 3, max = 20, message = "사용자명은 3-20자 사이여야 합니다")
    val username: String,

    @field:NotBlank(message = "비밀번호는 필수입니다")
    @field:Size(min = 3, message = "비밀번호는 최소 3자 이상이어야 합니다")
    val password: String,

    @field:NotBlank(message = "표시 이름은 필수입니다")
    @field:Size(min = 1, max = 50, message = "표시 이름은 1-50자 사이여야 합니다")
    val displayName: String
)

data class LoginRequest(
    @field:NotBlank(message = "사용자명은 필수입니다")
    val username: String,

    @field:NotBlank(message = "비밀번호는 필수입니다")
    val password: String
)
