package com.example.simpleblog.service;

import com.example.simpleblog.domain.Post;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.request.PostCreate;
import com.example.simpleblog.request.PostEdit;
import com.example.simpleblog.request.PostSearch;
import com.example.simpleblog.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Post post = postRepository.save(Post.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .build());

        // when
        PostResponse response = postService.getPost(post.getId());

        // then
        assertNotNull(post);
        assertEquals("글 제목입니다.", response.getTitle());
        assertEquals("글 내용입니다.", response.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void get_posts_list_test() {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                    .mapToObj(i -> Post.builder()
                                .title("제목 " + i)
                                .content("내용 " + i)
                                .build()
                    )
                    .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // when
        List<PostResponse> posts = postService.getPostList(
                PostSearch.builder()
                        .page(1)
                        .size(10)
                        .build()
        );

        // then
        assertEquals(10L, posts.size());
        assertEquals("제목 30", posts.get(0).getTitle());
        assertEquals("내용 30", posts.get(0).getContent());
        assertEquals("제목 26", posts.get(4).getTitle());
        assertEquals("내용 26", posts.get(4).getContent());
    }

    @Test
    @DisplayName("글 제목 수정")
    void update_post_title_test() {
        // given

        Post post = postRepository.save(Post.builder()
                .title("제목")
                .content("내용")
                .build());

        PostEdit postEdit = PostEdit.builder()
                .title("제목 수정")
                .build();


        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("제목 수정", changedPost.getTitle());
        assertEquals("내용", changedPost.getContent());
    }

    @Test
    @DisplayName("글 내용 수정")
    void update_post_content_test() {
        // given
        Post post = postRepository.save(Post.builder()
                .title("제목")
                .content("내용")
                .build());

        PostEdit postEdit = PostEdit.builder()
                .content("내용 수정")
                .build();


        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("제목", changedPost.getTitle());
        assertEquals("내용 수정", changedPost.getContent());
    }
}