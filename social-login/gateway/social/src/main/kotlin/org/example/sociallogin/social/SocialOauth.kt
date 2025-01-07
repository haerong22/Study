package org.example.sociallogin.social

import org.example.sociallogin.domain.social.OauthProvider
import reactor.core.publisher.Mono

interface SocialOauth {

    fun isSupported(oauthProvider: OauthProvider): Boolean
    fun getOauthUrl(): String
    fun getAccessToken(code: String): Mono<String>
}