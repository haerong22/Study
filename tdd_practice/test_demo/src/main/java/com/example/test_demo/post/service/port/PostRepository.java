package com.example.test_demo.post.service.port;

import com.example.test_demo.post.infrastructure.PostEntity;

import java.util.Optional;

public interface PostRepository {
    Optional<PostEntity> findById(long id);

    PostEntity save(PostEntity postEntity);
}
