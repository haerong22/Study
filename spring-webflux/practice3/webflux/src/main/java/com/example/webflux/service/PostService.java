package com.example.webflux.service;

import com.example.webflux.dto.PostResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PostService {

    Mono<PostResponse> getPostContent(Long id);

    Flux<PostResponse> getMultiplePostContent(List<Long> idList);

    Flux<PostResponse> getParallelMultiplePostContent(List<Long> idList);
}
