package org.example.sociallogin.domain.social

import reactor.core.publisher.Mono

interface SocialOauthService {

    fun getOauthUrl(oauthProvider: OauthProvider): Mono<String>
    fun getAccessToken(oauthProvider: OauthProvider, code: String): Mono<String>
    fun getUserInfo(oauthProvider: OauthProvider, accessToken: String): Mono<SocialUser>
}