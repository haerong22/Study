package com.example.webflux.client;

import com.example.webflux.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PostClient {

    private final WebClient webClient;
    private final String url = "http://localhost:8080";

    public Mono<PostResponse> getPost(Long id) {
        String uriString = UriComponentsBuilder.fromHttpUrl(url)
                .path("/sample/posts/%d".formatted(id))
                .buildAndExpand()
                .toUriString();

        return webClient.get()
                .uri(uriString)
                .retrieve()
                .bodyToMono(PostResponse.class);
    }
}
