package org.example.sociallogin.gateway.social.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoOauthDto(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Long,
    @JsonProperty("token_type")
    val tokenType: String,
)