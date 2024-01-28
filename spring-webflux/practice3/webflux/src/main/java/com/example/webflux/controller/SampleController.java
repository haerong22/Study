package com.example.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class SampleController {

    @GetMapping("/sample/hello")
    public Mono<String> getHello() {
        return Mono.just("hello rest contoller with webflux");
    }

    @GetMapping("/sample/posts/{id}")
    public Map<String ,String> getPosts(@PathVariable Long id) {
        return Map.of("id", id.toString(), "content", "Post content is %d".formatted(id));
    }
}
