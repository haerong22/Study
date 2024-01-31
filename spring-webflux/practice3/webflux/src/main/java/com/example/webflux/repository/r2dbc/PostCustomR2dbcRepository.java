package com.example.webflux.repository.r2dbc;

import com.example.webflux.repository.Post;
import reactor.core.publisher.Flux;

public interface PostCustomR2dbcRepository {

    Flux<Post> findAllByUserId(Long userId);
}
