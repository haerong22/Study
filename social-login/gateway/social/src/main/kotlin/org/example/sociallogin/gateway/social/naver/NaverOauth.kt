package org.example.sociallogin.gateway.social.naver

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth.naver")
data class NaverOauth(
    val oauthUrl: String,
    val accessTokenUrl: String,
    val userInfoUrl: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
)