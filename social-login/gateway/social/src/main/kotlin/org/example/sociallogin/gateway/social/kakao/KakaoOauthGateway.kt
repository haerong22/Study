package org.example.sociallogin.gateway.social.kakao

import org.example.sociallogin.domain.social.OauthProvider
import org.example.sociallogin.domain.social.SocialUser
import org.example.sociallogin.gateway.social.SocialOauth
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KakaoOauthGateway(
    private val kakaoOauth: KakaoOauth,
    private val webClient: WebClient,
) : SocialOauth {

    override fun isSupported(oauthProvider: OauthProvider): Boolean {
        return oauthProvider == OauthProvider.KAKAO
    }

    override fun getOauthUrl(): String {
        return "${kakaoOauth.oauthUrl}?response_type=code&client_id=${kakaoOauth.clientId}&redirect_uri=${kakaoOauth.redirectUri}"
    }

    override fun getAccessToken(code: String): Mono<String> {
        val params = LinkedMultiValueMap<String, String>().apply {
            "code" to code
            "client_id" to kakaoOauth.clientId
            "client_secret" to kakaoOauth.clientSecret
            "grant_type" to "authorization_code"
            "redirect_uri" to kakaoOauth.redirectUri
        }

        return webClient.post()
            .uri(kakaoOauth.accessTokenUrl)
            .header("Content-type","application/x-www-form-urlencoded;charset=utf-8" )
            .bodyValue("grant_type=authorization_code&client_id=${kakaoOauth.clientId}&client_secret=${kakaoOauth.clientSecret}&redirect_uri=${kakaoOauth.redirectUri}&code=${code}")
            .retrieve()
            .bodyToMono(KakaoOauthDto::class.java)
            .map { it.accessToken }
    }

    override fun getUserInfo(accessToken: String): Mono<SocialUser> {
        return webClient.get()
            .uri(kakaoOauth.userInfoUrl)
            .header("Content-type","application/x-www-form-urlencoded;charset=utf-8" )
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .bodyToMono(KakaoOauthUserDto::class.java)
            .map { SocialUser(id = it.id) }
    }
}