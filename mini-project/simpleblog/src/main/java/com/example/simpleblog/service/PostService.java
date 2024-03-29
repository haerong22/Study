package com.example.simpleblog.service;

import com.example.simpleblog.domain.Post;
import com.example.simpleblog.domain.PostEditor;
import com.example.simpleblog.exception.PostNotFound;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.request.PostCreate;
import com.example.simpleblog.request.PostEdit;
import com.example.simpleblog.request.PostSearch;
import com.example.simpleblog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .build();
    }

    public List<PostResponse> getPostList(PostSearch postSearch) {
//        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post postEntity = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder postEditorBuilder = postEntity.toEditor();

        PostEditor postEditor = postEditorBuilder
                .title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        postEntity.edit(postEditor);
    }

    public void delete(Long id) {
        Post postEntity = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(postEntity);
    }
}
