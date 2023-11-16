package com.example.test_demo.post.controller.response;

import com.example.test_demo.post.domain.Post;
import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostResponseTest {

    @Test
    @DisplayName("Post로 응답을 생성한다.")
    void postResponse_test() {
        // given
        Post post = Post.builder()
                .content("hello world")
                .writer(User.builder()
                        .email("test@test.com")
                        .nickname("bobby")
                        .address("seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("1234-1234-1234-1234")
                        .build())
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("hello world");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("test@test.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("bobby");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}