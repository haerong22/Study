package org.example.sociallogin.gateway.social

import org.example.sociallogin.domain.social.OauthProvider
import org.example.sociallogin.domain.social.SocialUser
import reactor.core.publisher.Mono

interface SocialOauth {

    fun isSupported(oauthProvider: OauthProvider): Boolean
    fun getOauthUrl(): String
    fun getAccessToken(code: String): Mono<String>
    fun getUserInfo(accessToken: String): Mono<SocialUser>
}