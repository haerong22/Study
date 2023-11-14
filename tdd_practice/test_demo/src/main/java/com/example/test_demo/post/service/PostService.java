package com.example.test_demo.post.service;

import com.example.test_demo.common.domain.exception.ResourceNotFoundException;
import com.example.test_demo.post.domain.PostCreate;
import com.example.test_demo.post.domain.PostUpdate;
import com.example.test_demo.post.infrastructure.PostEntity;
import com.example.test_demo.post.infrastructure.PostRepository;
import com.example.test_demo.user.infrastructure.UserEntity;
import java.time.Clock;

import com.example.test_demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity getPostById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public PostEntity create(PostCreate postCreate) {
        UserEntity userEntity = userService.getById(postCreate.getWriterId());
        PostEntity postEntity = new PostEntity();
        postEntity.setWriter(userEntity);
        postEntity.setContent(postCreate.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }

    public PostEntity update(long id, PostUpdate postUpdate) {
        PostEntity postEntity = getPostById(id);
        postEntity.setContent(postUpdate.getContent());
        postEntity.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }
}