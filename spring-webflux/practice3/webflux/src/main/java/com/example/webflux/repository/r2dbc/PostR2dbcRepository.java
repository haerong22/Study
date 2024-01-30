package com.example.webflux.repository.r2dbc;

import com.example.webflux.repository.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostR2dbcRepository extends ReactiveCrudRepository<Post, Long> {
}
