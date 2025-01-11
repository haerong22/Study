package org.example.sociallogin.gateway.social.naver

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverOauthDto(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Long,
    @JsonProperty("token_type")
    val tokenType: String,
)