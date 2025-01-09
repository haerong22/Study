package org.example.sociallogin.controller

import org.example.sociallogin.application.login.LoginService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class LoginController(
    private val loginService: LoginService,
) {

    @GetMapping("/api/login/{oauthProvider}")
    fun getOauthUrl(
        @PathVariable oauthProvider: String
    ): Mono<String> {
        return loginService.getOauthUrl(oauthProvider)
    }

    @GetMapping("/oauth/{oauthProvider}/approve")
    fun redirectUrl(
        @PathVariable oauthProvider: String,
        code: String
    ): Mono<String> {
        return loginService.getUserInfo(oauthProvider, code)
    }
}