package com.example.webflux.service;

import com.example.webflux.dto.PostResponse;
import reactor.core.publisher.Mono;

public interface PostService {

    Mono<PostResponse> getPostContent(Long id);
}
