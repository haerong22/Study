package com.example.webflux.service;

import com.example.webflux.client.PostClient;
import com.example.webflux.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostClient postClient;

    public Mono<PostResponse> getPostContent(Long id) {
        return postClient.getPost(id)
                .onErrorResume(e -> Mono.just(new PostResponse()));
    }

    @Override
    public Flux<PostResponse> getMultiplePostContent(List<Long> idList) {
        return Flux.fromIterable(idList)
                .flatMap(this::getPostContent)
                .log();
    }

    @Override
    public Flux<PostResponse> getParallelMultiplePostContent(List<Long> idList) {
        return Flux.fromIterable(idList)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(this::getPostContent)
                .log()
                .sequential();
    }
}
