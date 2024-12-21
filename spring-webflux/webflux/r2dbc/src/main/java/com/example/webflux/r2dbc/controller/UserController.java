package com.example.webflux.r2dbc.controller;

import com.example.webflux.r2dbc.controller.dto.ProfileImageResponse;
import com.example.webflux.r2dbc.controller.dto.UserResponse;
import com.example.webflux.r2dbc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
                            .map(user -> {
                                log.info("user: {}", user);
                                return new UserResponse(
                                        user.getId(),
                                        user.getName(),
                                        user.getAge(),
                                        new ProfileImageResponse(
                                                user.getProfileImage().getId(),
                                                user.getProfileImage().getName(),
                                                user.getProfileImage().getUrl()
                                        )
                                );
                            })
                            .switchIfEmpty(
                                    Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
                            );
                });
    }
}
