package com.example.webflux.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository {

    Mono<Post> save(Post post);

    Flux<Post> findAll();

    Mono<Post> findById(Long id);

    Flux<Post> findAllByUserId(Long id);

    Mono<Void> deleteById(Long id);
}
