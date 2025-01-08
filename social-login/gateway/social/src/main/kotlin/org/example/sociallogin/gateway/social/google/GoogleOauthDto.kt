package org.example.sociallogin.gateway.social.google

import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleOauthDto(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Long,
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("id_token")
    val idToken: String,
)