package com.example.webflux.r2dbc.controller

import com.example.webflux.r2dbc.common.User
import com.example.webflux.r2dbc.controller.dto.SignupUserRequest
import com.example.webflux.r2dbc.controller.dto.UserResponse
import com.example.webflux.r2dbc.service.UserCoroutineService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RequestMapping("/api/users")
@RestController
class UserController(
    private val userService: UserCoroutineService,
) {

    @GetMapping("/{userId}")
    fun getUserById(
        @PathVariable userId: String
    ): Mono<UserResponse> {
        return ReactiveSecurityContextHolder.getContext()
            .flatMap { context: SecurityContext ->
                val name = context.authentication.name
                if (name != userId) {
                    return@flatMap Mono.error<UserResponse>(
                        ResponseStatusException(HttpStatus.UNAUTHORIZED)
                    )
                }
                userService.findById(userId)
                    .map { user: User -> UserResponse.of(user) }
                    .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
            }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(
        @RequestBody request: SignupUserRequest
    ): Mono<UserResponse> {
        return userService.createUser(request.name, request.age, request.password, request.profileImageId)
            .map { user: User -> UserResponse.of(user) }
    }
}