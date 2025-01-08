package org.example.sociallogin.gateway.social.google

import org.example.sociallogin.domain.social.OauthProvider
import org.example.sociallogin.domain.social.SocialUser
import org.example.sociallogin.gateway.social.SocialOauth
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class GoogleOauthGateway(
    private val googleOauth: GoogleOauth,
    private val webClient: WebClient,
): SocialOauth {

    override fun isSupported(oauthProvider: OauthProvider): Boolean {
        return oauthProvider == OauthProvider.GOOGLE
    }

    override fun getOauthUrl(): String {
        return "${googleOauth.oauthUrl}?scope=${googleOauth.scope}&response_type=code&client_id=${googleOauth.clientId}&redirect_uri=${googleOauth.redirectUri}"
    }

    override fun getAccessToken(code: String): Mono<String> {
        val body = mapOf(
            "code" to code,
            "client_id" to googleOauth.clientId,
            "client_secret" to googleOauth.clientSecret,
            "grant_type" to "authorization_code",
            "redirect_uri" to googleOauth.redirectUri,
        )

        return webClient.post()
            .uri(googleOauth.accessTokenUrl)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(GoogleOauthDto::class.java)
            .map { it.accessToken }
    }

    override fun getUserInfo(accessToken: String): Mono<SocialUser> {
        return webClient.get()
            .uri(googleOauth.userInfoUrl)
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .bodyToMono(SocialUser::class.java)
    }
}