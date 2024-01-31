package com.example.webflux.controller;

import com.example.webflux.dto.PostCreateRequest;
import com.example.webflux.dto.PostResponseV2;
import com.example.webflux.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/posts")
public class PostControllerV2 {

    private final PostService postService;

    @PostMapping
    public Mono<PostResponseV2> createPost(@RequestBody PostCreateRequest request) {
        return postService.create(request.getUserId(), request.getTitle(), request.getContent())
                .map(PostResponseV2::of);
    }

    @GetMapping
    public Flux<PostResponseV2> findAllPost() {
        return postService.findAll()
                .map(PostResponseV2::of);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PostResponseV2>> findPost(@PathVariable Long id) {
        return postService.findById(id)
                .map(p -> ResponseEntity.ok().body(PostResponseV2.of(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> deletePost(@PathVariable Long id) {
        return postService.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }
}
