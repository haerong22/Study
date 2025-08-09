package com.chat.domain.service

import com.chat.domain.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {

    // 사용자 관리
    fun createUser(request: CreateUserRequest): UserDto
    fun login(request: LoginRequest): UserDto
    fun getUserById(userId: Long): UserDto
    fun searchUsers(query: String, pageable: Pageable): Page<UserDto>

    // 사용자 상태
    fun updateLastSeen(userId: Long): UserDto
}