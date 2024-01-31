package com.example.webflux.service;

import com.example.webflux.repository.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

    Mono<Post> create(Long userId, String title, String content);

    Flux<Post> findAll();

    Mono<Post> findById(Long id);

    Flux<Post> findAllByUserId(Long id);

    Mono<Void> deleteById(Long id);
}
