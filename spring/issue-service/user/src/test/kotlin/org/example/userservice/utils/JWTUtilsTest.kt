package org.example.userservice.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.example.userservice.config.JWTProperties
import org.junit.jupiter.api.Test

class JWTUtilsTest {

    private val log = KotlinLogging.logger {}

    @Test
    fun createTokenTest() {
        // given
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "test@email.com",
            profileUrl = "profile.png",
            username = "test",
        )

        val properties = JWTProperties(
            issuer = "jara",
            subject = "auth",
            expiresTime = 3600,
            secret = "my-secret",
        )

        // when
        val token = JWTUtils.createToken(jwtClaim, properties)

        // then
        assertThat(token).isNotNull()

        log.info { "token : $token" }
    }

    @Test
    fun decodeTest() {
        // given
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "test@email.com",
            profileUrl = "profile.png",
            username = "test",
        )

        val properties = JWTProperties(
            issuer = "jara",
            subject = "auth",
            expiresTime = 3600,
            secret = "my-secret",
        )

        val token = JWTUtils.createToken(jwtClaim, properties)

        // when
        val decode = JWTUtils.decode(token = token, secret = properties.secret, issuer = properties.issuer)

        // then
        with(decode) {
            log.info { "claims : $claims" }

            val userId = claims["userId"]!!.asLong()
            assertThat(userId).isEqualTo(jwtClaim.userId)

            val email = claims["email"]!!.asString()
            assertThat(email).isEqualTo(jwtClaim.email)

            val profileUrl = claims["profileUrl"]!!.asString()
            assertThat(profileUrl).isEqualTo(jwtClaim.profileUrl)

            val username = claims["username"]!!.asString()
            assertThat(username).isEqualTo(jwtClaim.username)
        }
    }
}