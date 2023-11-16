package com.example.test_demo.post.service;

import com.example.test_demo.common.domain.exception.ResourceNotFoundException;
import com.example.test_demo.common.service.port.ClockHolder;
import com.example.test_demo.post.controller.port.PostService;
import com.example.test_demo.post.domain.Post;
import com.example.test_demo.post.domain.PostCreate;
import com.example.test_demo.post.domain.PostUpdate;
import com.example.test_demo.post.service.port.PostRepository;
import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    @Override
    public Post getPostById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    @Override
    public Post create(PostCreate postCreate) {
        User writer = userRepository.getById(postCreate.getWriterId());
        Post post = Post.from(writer, postCreate, clockHolder);
        return postRepository.save(post);
    }

    @Override
    public Post update(long id, PostUpdate postUpdate) {
        Post post = getPostById(id);
        return postRepository.save(post.update(postUpdate, clockHolder));
    }
}