package org.example.sociallogin.social.google

import org.example.sociallogin.domain.social.OauthProvider
import org.example.sociallogin.social.SocialOauth
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class GoogleOauthGateway(
    private val googleOauth: GoogleOauth,
    private val googleWebClient: WebClient,
): SocialOauth {

    override fun isSupported(oauthProvider: OauthProvider): Boolean {
        return oauthProvider == OauthProvider.GOOGLE
    }

    override fun getOauthUrl(): String {
        return "${googleOauth.url}?scope=profile&response_type=code&client_id=${googleOauth.clientId}&redirect_uri=${googleOauth.redirectUri}"
    }

    override fun getAccessToken(code: String): Mono<String> {
        val body = mapOf(
            "code" to code,
            "client_id" to googleOauth.clientId,
            "client_secret" to googleOauth.clientSecret,
            "grant_type" to "authorization_code",
            "redirect_uri" to googleOauth.redirectUri,
        )

        val result = googleWebClient.post()
            .uri("/token")
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String::class.java)

        return result
    }
}