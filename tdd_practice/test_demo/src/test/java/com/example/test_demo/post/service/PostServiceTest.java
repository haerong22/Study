package com.example.test_demo.post.service;

import com.example.test_demo.mock.FakePostRepository;
import com.example.test_demo.mock.FakeUserRepository;
import com.example.test_demo.mock.TestClockHolder;
import com.example.test_demo.post.domain.Post;
import com.example.test_demo.post.domain.PostCreate;
import com.example.test_demo.post.domain.PostUpdate;
import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest {

    private PostServiceImpl postService;

    @BeforeEach
    void init() {
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        User user1 = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("bobby")
                .address("seoul")
                .certificationCode("1234-1234-1234-1234")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        User user2 = User.builder()
                .id(2L)
                .email("test2@test.com")
                .nickname("bobby2")
                .address("busan")
                .certificationCode("4321-1234-1234-1234")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build();
        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);

        FakePostRepository fakePostRepository = new FakePostRepository();
        Post post1 = Post.builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .writer(user1)
                .build();
        fakePostRepository.save(post1);

        this.postService = PostServiceImpl.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();
    }

    @Test
    @DisplayName("getPostById 는 존재하는 게시물 조회")
    void getPostById_success() {
        // given
        long id = 1;

        // when
        Post result = postService.getPostById(id);

        // then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getWriter().getEmail()).isEqualTo("test@test.com");
    }

    @Test
    @DisplayName("PostCreateDto 를 이용하여 게시물 생성")
    void create_success() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("test")
                .build();

        // when
        Post result = postService.create(postCreate);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("test");
        assertThat(result.getCreatedAt()).isEqualTo(1678530673958L);
    }

    @Test
    @DisplayName("PostUpdateDto 를 이용하여 게시물 수정")
    void update_success() {
        // given
        PostUpdate userUpdateDto = PostUpdate.builder()
                .content("update content")
                .build();

        // when
        postService.update(1, userUpdateDto);

        // then
        Post postEntity = postService.getPostById(1);
        assertThat(postEntity.getContent()).isEqualTo("update content");
        assertThat(postEntity.getModifiedAt()).isEqualTo(1678530673958L);
    }

}