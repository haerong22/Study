package com.example.simpleblog.controller;

import com.example.simpleblog.domain.Post;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 저장된 값을 출력한다.")
    void posts() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .build();

        String jsonString = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title값은 필수다.")
    void posts_validation() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .content("글 내용입니다.")
                .build();

        String jsonString = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목을 입력해주세요."))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void save_post_success() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .build();

        String jsonString = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString)
                )
                .andExpect(status().isOk())
                .andDo(print())
        ;

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("글 제목입니다.", post.getTitle());
        assertEquals("글 내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("postId 로 글 조회")
    void get_post_by_id_success() throws Exception {
        // given
        Post post = Post.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("글 제목입니다."))
                .andExpect(jsonPath("$.content").value("글 내용입니다."))
                .andDo(print())
        ;

    }
}