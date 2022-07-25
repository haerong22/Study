package com.example.simpleblog.service;

import com.example.simpleblog.domain.Post;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.request.PostCreate;
import com.example.simpleblog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse getPost(Long id) {
        Post postEntity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .build();
    }

    public List<PostResponse> getPostList() {
        return postRepository.findAll().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
