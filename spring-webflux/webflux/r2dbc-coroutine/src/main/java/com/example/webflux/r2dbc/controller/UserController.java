package com.example.webflux.r2dbc.controller;

import com.example.webflux.r2dbc.controller.dto.SignupUserRequest;
import com.example.webflux.r2dbc.controller.dto.UserResponse;
import com.example.webflux.r2dbc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public Mono<UserResponse> getUserById(
            @PathVariable String userId
    ) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(context -> {
                    String name = context.getAuthentication().getName();

                    if (!name.equals(userId)) {
                        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
                    }

                    return userService.findById(userId)
                            .map(UserResponse::of)
                            .switchIfEmpty(
                                    Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
                            );
                });
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public Mono<UserResponse> signup(
            @RequestBody SignupUserRequest request
    ) {
        return userService.createUser(
                        request.getName(), request.getAge(), request.getPassword(), request.getProfileImageId()
                )
                .map(UserResponse::of);
    }
}
