package com.example.webflux.controller;

import com.example.webflux.dto.PostResponse;
import com.example.webflux.service.PostServiceEx;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostServiceEx postService;

    @GetMapping("/{id}")
    public Mono<PostResponse> getPostContent(@PathVariable Long id) {
        return postService.getPostContent(id);
    }

    @GetMapping("/search")
    public Flux<PostResponse> getMultiplePostContent(@RequestParam(name = "ids") List<Long> idList) {
        return postService.getMultiplePostContent(idList);
    }

    @GetMapping("/search/parallel")
    public Flux<PostResponse> getParallelMultiplePostContent(@RequestParam(name = "ids") List<Long> idList) {
        return postService.getParallelMultiplePostContent(idList);
    }
}
