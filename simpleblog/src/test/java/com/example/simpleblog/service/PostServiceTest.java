package com.example.simpleblog.service;

import com.example.simpleblog.domain.Post;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.request.PostCreate;
import com.example.simpleblog.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void post_service_test() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("글 제목입니다.", post.getTitle());
        assertEquals("글 내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("postId 로 글 조회")
    void get_post_by_id_test() {
        // given
        Post post = Post.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .build();

        postRepository.save(post);

        Long postId = 1L;

        // when
        PostResponse response = postService.getPost(postId);

        // then
        assertNotNull(post);
        assertEquals("글 제목입니다.", response.getTitle());
        assertEquals("글 내용입니다.", response.getContent());
    }
}