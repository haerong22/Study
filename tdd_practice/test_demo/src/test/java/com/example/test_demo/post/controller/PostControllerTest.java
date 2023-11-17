package com.example.test_demo.post.controller;

import com.example.test_demo.common.domain.exception.ResourceNotFoundException;
import com.example.test_demo.mock.TestClockHolder;
import com.example.test_demo.mock.TestContainer;
import com.example.test_demo.mock.TestUuidHolder;
import com.example.test_demo.post.controller.response.PostResponse;
import com.example.test_demo.post.domain.Post;
import com.example.test_demo.post.domain.PostUpdate;
import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest {

    @Test
    @DisplayName("유저는 게시물을 조회 할 수 있다.")
    void getPostById_200() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("bobby")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("1234-1234-1234-1234")
                .lastLoginAt(100L)
                .build();
        testContainer.userRepository.save(user);

        Post post = Post.builder()
                .id(1L)
                .content("helloworld")
                .writer(user)
                .createdAt(100L)
                .build();

        testContainer.postRepository.save(post);

        // when
        ResponseEntity<PostResponse> result = testContainer.postController.getPostById(1L);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("test@test.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("bobby");
    }

    @Test
    @DisplayName("존재하지 않는 게시물 호출 시 404 응답 받는다.")
    void getPostById_404() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        // when

        // then
        assertThatThrownBy(() -> {
            testContainer.postController.getPostById(1234);
        })
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Posts에서 ID 1234를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("유저는 게시물을 수정 할 수 있다.")
    void updatePost_200() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();

        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("bobby")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("1234-1234-1234-1234")
                .lastLoginAt(100L)
                .build();
        testContainer.userRepository.save(user);

        Post post = Post.builder()
                .id(1L)
                .content("helloworld")
                .writer(user)
                .createdAt(100L)
                .build();

        testContainer.postRepository.save(post);

        PostUpdate postUpdate = PostUpdate.builder()
                .content("update")
                .build();

        // when
        ResponseEntity<PostResponse> result = testContainer.postController.updatePost(1, postUpdate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getContent()).isEqualTo("update");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
        assertThat(result.getBody().getModifiedAt()).isEqualTo(1678530673958L);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("test@test.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("bobby");
    }
}