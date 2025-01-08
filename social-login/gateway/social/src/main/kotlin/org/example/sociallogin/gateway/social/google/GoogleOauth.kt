package org.example.sociallogin.gateway.social.google

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth.google")
data class GoogleOauth(
    val oauthUrl: String,
    val accessTokenUrl: String,
    val userInfoUrl: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val scope: String,
)