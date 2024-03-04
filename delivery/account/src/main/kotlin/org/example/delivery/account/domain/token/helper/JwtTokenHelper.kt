package org.example.delivery.account.domain.token.helper

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.example.delivery.account.domain.token.interfaces.TokenHelper
import org.example.delivery.account.domain.token.model.TokenDto
import org.example.delivery.common.error.TokenErrorCode
import org.example.delivery.common.exception.ApiException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.HashMap

@Component
class JwtTokenHelper : TokenHelper {

    @Value("\${token.secret.key}")
    private val secretKey: String? = null

    @Value("\${token.access-token.expired-hour}")
    private val accessTokenExpiredHour: Long = 1

    @Value("\${token.refresh-token.expired-hour}")
    private val refreshTokenExpiredHour: Long = 12

    override fun issueAccessToken(data: Map<String, Any>): TokenDto? {
        val expired = LocalDateTime.now().plusDays(accessTokenExpiredHour)
        val expiredAt = expired.let {
            Date.from(it.atZone(ZoneId.systemDefault()).toInstant())
        }

        val key = Keys.hmacShaKeyFor(secretKey?.toByteArray())

        val jwt = Jwts.builder()
            .signWith(key, Jwts.SIG.HS256)
            .claims(data)
            .expiration(expiredAt)
            .compact();

        return TokenDto(
            token = jwt,
            expiredAt = expired
        )
    }

    override fun issueRefreshToken(data: Map<String, Any>): TokenDto? {
        val expired = LocalDateTime.now().plusDays(refreshTokenExpiredHour)
        val expiredAt = expired.let {
            Date.from(it.atZone(ZoneId.systemDefault()).toInstant())
        }

        val key = Keys.hmacShaKeyFor(secretKey?.toByteArray())

        val jwt = Jwts.builder()
            .signWith(key, Jwts.SIG.HS256)
            .claims(data)
            .expiration(expiredAt)
            .compact();

        return TokenDto(
            token = jwt,
            expiredAt = expired
        )
    }

    override fun validationTokenWithThrow(token: String?): Map<String, Any>? {
        val key = Keys.hmacShaKeyFor(secretKey?.toByteArray())

        val parser = Jwts.parser().verifyWith(key).build()

        return try {
            val result = token?.let {  parser.parseSignedClaims(it) }
            HashMap(result?.payload)
        } catch (e: Exception) {
            when(e) {
                is SignatureException -> {
                    throw ApiException(TokenErrorCode.INVALID_TOKEN, e)
                }
                is ExpiredJwtException -> {
                    throw ApiException(TokenErrorCode.EXPIRED_TOKEN, e)
                }
                else -> {
                    throw ApiException(TokenErrorCode.TOKEN_EXCEPTION, e)
                }
            }
        }
    }
}