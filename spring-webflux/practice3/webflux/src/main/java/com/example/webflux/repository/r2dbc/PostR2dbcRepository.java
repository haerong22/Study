package com.example.webflux.repository.r2dbc;

import com.example.webflux.repository.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PostR2dbcRepository extends ReactiveCrudRepository<Post, Long>, PostCustomR2dbcRepository {

    Flux<Post> findByUserId(Long id);
}
