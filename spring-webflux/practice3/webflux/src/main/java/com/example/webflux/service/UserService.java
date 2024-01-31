package com.example.webflux.service;

import com.example.webflux.repository.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> create(String name, String email);

    Flux<User> findAll();

    Mono<User> findById(Long id);

    Mono<Void> deleteById(Long id);

    Mono<Void> deleteByName(String name);

    Mono<User> update(Long id, String name, String email);
}
