package org.example.sociallogin.application.login

import org.example.sociallogin.domain.social.OauthProvider
import org.example.sociallogin.domain.social.SocialOauthService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class LoginService(
    private val socialOauthService: SocialOauthService,
) {
    fun getOauthUrl(oauthProvider: String): Mono<String> {
        return socialOauthService.getOauthUrl(OauthProvider.of(oauthProvider))
    }

    fun getAccessToken(oauthProvider: String, code: String): Mono<String> {
        return socialOauthService.getAccessToken(OauthProvider.of(oauthProvider), code)
    }
}