package org.example.userservice.service

import org.example.userservice.config.JWTProperties
import org.example.userservice.domain.entity.User
import org.example.userservice.domain.repository.UserRepository
import org.example.userservice.exception.PasswordNotMatchedException
import org.example.userservice.exception.UserExistsException
import org.example.userservice.exception.UserNotFoundException
import org.example.userservice.model.SignInRequest
import org.example.userservice.model.SignInResponse
import org.example.userservice.model.SignUpRequest
import org.example.userservice.utils.BCryptUtils
import org.example.userservice.utils.JWTClaim
import org.example.userservice.utils.JWTUtils
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
    private val cacheManager: CoroutineCacheManager<User>,
) {

    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

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

    suspend fun signIn(signInRequest: SignInRequest): SignInResponse {
        return with(userRepository.findByEmail(signInRequest.email) ?: throw UserNotFoundException()) {
            val verified = BCryptUtils.verify(signInRequest.password, password)

            if (!verified) throw PasswordNotMatchedException()

            val jwtClaim = JWTClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username,
            )

            val token = JWTUtils.createToken(jwtClaim, jwtProperties)

            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)

            SignInResponse(
                email = email,
                username = username,
                token = token,
            )
        }
    }

    suspend fun logout(token: String) {
        cacheManager.awaitEvict(token)
    }
}