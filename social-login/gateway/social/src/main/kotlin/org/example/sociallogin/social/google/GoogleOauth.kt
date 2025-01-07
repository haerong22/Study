package org.example.sociallogin.social.google

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth.google")
data class GoogleOauth(
    val url: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val scope: String,
)