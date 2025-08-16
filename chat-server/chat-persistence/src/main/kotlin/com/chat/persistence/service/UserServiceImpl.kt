package com.chat.persistence.service

import com.chat.domain.dto.CreateUserRequest
import com.chat.domain.dto.LoginRequest
import com.chat.domain.dto.UserDto
import com.chat.domain.model.User
import com.chat.domain.service.UserService
import com.chat.persistence.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest
import java.time.LocalDateTime

@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun createUser(request: CreateUserRequest): UserDto {
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("이미 존재하는 사용자명입니다: ${request.username}")
        }

        val user = User(
            username = request.username,
            password = hashPassword(request.password),
            displayName = request.displayName,
        )

        val savedUser = userRepository.save(user)
        return userToDto(savedUser)
    }

    override fun login(request: LoginRequest): UserDto {
        TODO("Not yet implemented")
    }

    override fun getUserById(userId: Long): UserDto {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다: $userId") }
        return userToDto(user)
    }

    override fun searchUsers(query: String, pageable: Pageable): Page<UserDto> {
        return userRepository.searchUsers(query, pageable).map { userToDto(it) }
    }

    override fun updateLastSeen(userId: Long): UserDto {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다: $userId") }

        val now = LocalDateTime.now()
        userRepository.updateLastSeenAt(userId, now)

        return userToDto(user.copy(lastSeenAt = now))
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
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
            createdAt = user.createdAt,
        )
    }
}