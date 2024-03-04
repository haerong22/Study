package org.example.delivery.account.domain.token.service

import org.example.delivery.account.domain.token.interfaces.TokenHelper
import org.example.delivery.account.domain.token.model.TokenDto
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val tokenHelper: TokenHelper
) {

    fun issueAccessToken(userId: Long?): TokenDto? {
        return userId?.let {
            val data = mapOf("userId" to it)
            tokenHelper.issueAccessToken(data)
        }
    }

    fun issueRefreshToken(userId: Long?): TokenDto? {
        requireNotNull(userId)

        val data = mapOf("userId" to userId)
        return tokenHelper.issueAccessToken(data)
    }

    fun validationToken(token: String?): Long? {
        return token?.let { t ->
            tokenHelper.validationTokenWithThrow(t)
        }?.let {
            map -> map["userId"]
        }?.let { userId ->
            userId.toString().toLong()
        }
    }
}