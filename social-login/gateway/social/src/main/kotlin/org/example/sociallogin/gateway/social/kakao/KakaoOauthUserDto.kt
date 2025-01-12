package org.example.sociallogin.gateway.social.kakao

data class KakaoOauthUserDto(
    val id: String,
    val kakaoAccount: KakaoAccount?,
) {

    data class KakaoAccount(
        val name: String?,
        val email: String?,
    )
}