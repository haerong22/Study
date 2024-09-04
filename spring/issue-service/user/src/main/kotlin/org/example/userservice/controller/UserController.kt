package org.example.userservice.controller

import org.example.userservice.model.AuthToken
import org.example.userservice.model.SignInRequest
import org.example.userservice.model.SignInResponse
import org.example.userservice.model.SignUpRequest
import org.example.userservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/signup")
    suspend fun signUp(
        @RequestBody request: SignUpRequest,
    ) {
        userService.signUp(request)
    }

    @PostMapping("/signin")
    suspend fun signIn(
        @RequestBody signInRequest: SignInRequest,
    ) : SignInResponse {
        return userService.signIn(signInRequest)
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun logout(
        @AuthToken token: String,
    ) {
        userService.logout(token)
    }
}