package org.example.sociallogin.gateway.social.kakao

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth.kakao")
data class KakaoOauth(
    val oauthUrl: String,
    val accessTokenUrl: String,
    val userInfoUrl: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
)