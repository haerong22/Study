package com.example.test_demo.post.domain;

import com.example.test_demo.mock.TestClockHolder;
import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Test
    @DisplayName("PostCreate로 게시물을 생성한다.")
    void postCreate_test() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("hello world")
                .build();

        User writer = User.builder()
                .email("test@test.com")
                .nickname("bobby")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("1234-1234-1234-1234")
                .build();

        // when
        Post post = Post.from(writer, postCreate, new TestClockHolder(1678530673958L));

        // then
        assertThat(post.getContent()).isEqualTo("hello world");
        assertThat(post.getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(post.getWriter().getEmail()).isEqualTo("test@test.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("bobby");
        assertThat(post.getWriter().getAddress()).isEqualTo("seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("1234-1234-1234-1234");

    }

    @Test
    @DisplayName("PostUpdate로 게시물을 수정한다.")
    void postUpdate_test() {
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

        PostUpdate postUpdate = PostUpdate.builder()
                .content("hello world!!")
                .build();

        // when
        post = post.update(postUpdate, new TestClockHolder(1678530673958L));

        // then
        assertThat(post.getContent()).isEqualTo("hello world!!");
        assertThat(post.getModifiedAt()).isEqualTo(1678530673958L);
        assertThat(post.getWriter().getEmail()).isEqualTo("test@test.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("bobby");
        assertThat(post.getWriter().getAddress()).isEqualTo("seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("1234-1234-1234-1234");

    }
}