package com.example.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
public class SampleController {

    @GetMapping("/sample/hello")
    public Mono<String> getHello() {
        return Mono.just("hello rest contoller with webflux");
    }

    @GetMapping("/sample/posts/{id}")
    public Map<String ,String> getPosts(@PathVariable Long id) throws InterruptedException {
        log.info("post id is {}", id);
        Thread.sleep(300);

        if (id > 10) {
            throw new RuntimeException("Too long");
        }

        return Map.of("id", id.toString(), "content", "Post content is %d".formatted(id));
    }
}
