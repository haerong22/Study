package org.example.userservice.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JWTProperties(
    val issuer: String,
    val subject: String,
    val expiresTime: Long,
    val secret: String,
)