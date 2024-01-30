package com.example.webflux.repository;

import com.example.webflux.repository.r2dbc.PostR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PostRepositoryR2dbcImpl implements PostRepository {

    private final PostR2dbcRepository postR2dbcRepository;

    @Override
    public Mono<Post> save(Post post) {
        return postR2dbcRepository.save(post);
    }

    @Override
    public Flux<Post> findAll() {
        return postR2dbcRepository.findAll();
    }

    @Override
    public Mono<Post> findById(Long id) {
        return postR2dbcRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return postR2dbcRepository.deleteById(id);
    }
}
