package org.example.sociallogin.gateway.social.naver

import org.example.sociallogin.domain.social.OauthProvider
import org.example.sociallogin.domain.social.SocialUser
import org.example.sociallogin.gateway.social.SocialOauth
import org.example.sociallogin.gateway.social.google.GoogleOauthDto
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class NaverOauthGateway(
    private val naverOauth: NaverOauth,
    private val webClient: WebClient,
) : SocialOauth {

    override fun isSupported(oauthProvider: OauthProvider): Boolean {
        return oauthProvider == OauthProvider.NAVER
    }

    override fun getOauthUrl(): String {
        return "${naverOauth.oauthUrl}?response_type=code&client_id=${naverOauth.clientId}&redirect_uri=${naverOauth.redirectUri}&state=test"
    }

    override fun getAccessToken(code: String): Mono<String> {
        return webClient.get()
            .uri("${naverOauth.accessTokenUrl}?code=$code&client_id=${naverOauth.clientId}&client_secret=${naverOauth.clientSecret}&grant_type=authorization_code&redirect_uri=${naverOauth.redirectUri}&state=test")
            .retrieve()
            .bodyToMono(NaverOauthDto::class.java)
            .map { it.accessToken }
    }

    override fun getUserInfo(accessToken: String): Mono<SocialUser> {
        return webClient.get()
            .uri(naverOauth.userInfoUrl)
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .bodyToMono(NaverOauthUserDto::class.java)
            .map { it.response }
    }
}