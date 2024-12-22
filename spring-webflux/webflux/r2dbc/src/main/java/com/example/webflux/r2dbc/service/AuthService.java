package com.example.webflux.r2dbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final R2dbcEntityTemplate entityTemplate;

    private static final Map<String, String> tokenUserIdMap = Map.of("abcd", "1");

    public Mono<String> getNameByToken(String token) {
        return Mono.justOrEmpty(tokenUserIdMap.get(token));
    }
}
