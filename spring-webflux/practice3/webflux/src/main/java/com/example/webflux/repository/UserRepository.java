package com.example.webflux.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> save(User user);

    Flux<User> findAll();

    Mono<User> findById(Long id);

    Mono<Void> deleteById(Long id);

    Mono<Void> deleteByName(String name);
}
