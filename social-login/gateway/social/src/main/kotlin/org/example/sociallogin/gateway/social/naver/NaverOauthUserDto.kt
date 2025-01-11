package org.example.sociallogin.gateway.social.naver

import org.example.sociallogin.domain.social.SocialUser

data class NaverOauthUserDto(
    val resultcode: String,
    val message: String,
    val response: SocialUser,
)