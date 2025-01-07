package org.example.sociallogin.social

import org.example.sociallogin.domain.social.OauthProvider
import org.example.sociallogin.domain.social.SocialOauthService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SocialOauthServiceImpl(
    private val socialOauthGateways: List<SocialOauth>
): SocialOauthService {

    override fun getOauthUrl(oauthProvider: OauthProvider): Mono<String> {
        return Mono.just(
            socialOauthGateways.first { it.isSupported(oauthProvider) }.getOauthUrl()
        )
    }

    override fun getAccessToken(oauthProvider: OauthProvider, code: String): Mono<String> {
        return socialOauthGateways.first { it.isSupported(oauthProvider) }.getAccessToken(code)
    }
}