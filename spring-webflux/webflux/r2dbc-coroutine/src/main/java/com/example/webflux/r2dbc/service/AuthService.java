package com.example.webflux.r2dbc.service;

import com.example.webflux.r2dbc.common.repository.AuthEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final R2dbcEntityTemplate entityTemplate;

    public Mono<String> getNameByToken(String token) {
        var query = Query.query(
                Criteria.where("token").is(token)
        );

        return entityTemplate.select(AuthEntity.class)
                .matching(query)
                .one()
                .map(authEntity -> authEntity.getUserId().toString())
                .doOnNext(name -> log.info("name: {}", name));
    }
}
