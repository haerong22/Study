package com.example.test_demo.post.controller;

import com.example.test_demo.mock.TestClockHolder;
import com.example.test_demo.mock.TestContainer;
import com.example.test_demo.mock.TestUuidHolder;
import com.example.test_demo.post.controller.response.PostResponse;
import com.example.test_demo.post.domain.PostCreate;
import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class PostCreateControllerTest {

    @Test
    @DisplayName("유저는 게시물을 작성할 수 있다.")
    void createPost_201() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new TestClockHolder(1678530673958L))
                .uuidHolder(new TestUuidHolder("1234-1234-1234-1234"))
                .build();

        testContainer.userRepository.save(
                User.builder()
                        .id(1L)
                        .email("test@test.com")
                        .nickname("bobby")
                        .address("seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("1234-1234-1234-1234")
                        .lastLoginAt(100L)
                        .build()
        );

        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();

        // when
        ResponseEntity<PostResponse> result = testContainer.postCreateController.create(postCreate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("test@test.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("bobby");
    }
}