package org.example.userservice.service

import org.example.userservice.domain.entity.User
import org.example.userservice.domain.repository.UserRepository
import org.example.userservice.exception.UserExistsException
import org.example.userservice.model.SignUpRequest
import org.example.userservice.utils.BCryptUtils
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    suspend fun signUp(signUpRequest: SignUpRequest) {

        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistsException()
            }

            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username,
            )

            userRepository.save(user)
        }
    }
}