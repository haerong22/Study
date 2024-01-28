package com.example.webflux.service;

import com.example.webflux.client.PostClient;
import com.example.webflux.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostClient postClient;

    public Mono<PostResponse> getPostContent(Long id) {
        return postClient.getPost(id);
    }
}
