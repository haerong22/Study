package com.example.webflux.service;

import com.example.webflux.repository.Post;
import com.example.webflux.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Mono<Post> create(Long userId, String title, String content) {
        return postRepository.save(Post.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .build());
    }

    @Override
    public Flux<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Mono<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Flux<Post> findAllByUserId(Long id) {
        return postRepository.findAllByUserId(id);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return postRepository.deleteById(id);
    }

}
