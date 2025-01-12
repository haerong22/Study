package org.example.sociallogin.application.login

import org.example.sociallogin.domain.social.OauthProvider
import org.example.sociallogin.domain.social.SocialOauthService
import org.example.sociallogin.domain.user.User
import org.example.sociallogin.domain.user.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class LoginService(
    private val socialOauthService: SocialOauthService,
    private val userRepository: UserRepository,
) {
    fun getOauthUrl(oauthProvider: String): Mono<String> {
        return socialOauthService.getOauthUrl(OauthProvider.of(oauthProvider))
    }

    fun getUserInfo(oauthProvider: String, code: String): Mono<String> {
        val provider = OauthProvider.of(oauthProvider)
        return socialOauthService.getAccessToken(provider, code)
            .flatMap { socialOauthService.getUserInfo(provider, it) }
            .flatMap { userRepository.save(User(name = it.name, email = it.email, socialId = it.id, provider = provider)) }
            .map { it.socialId }
    }
}