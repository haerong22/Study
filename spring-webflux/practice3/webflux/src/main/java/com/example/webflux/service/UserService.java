package com.example.webflux.service;

import com.example.webflux.repository.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> create(String name, String email);

    Flux<User> findAll();

    Mono<User> findById(Long id);

    Mono<Integer> deleteById(Long id);

    Mono<User> update(Long id, String name, String email);
}
