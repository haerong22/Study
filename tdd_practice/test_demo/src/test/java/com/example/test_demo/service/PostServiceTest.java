package com.example.test_demo.service;

import com.example.test_demo.model.dto.PostCreateDto;
import com.example.test_demo.model.dto.PostUpdateDto;
import com.example.test_demo.repository.PostEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@SqlGroup({
        @Sql(value = "/sql/post-service-test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = AFTER_TEST_METHOD)
})
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("getPostById 는 존재하는 게시물 조회")
    void getPostById_success() {
        // given
        long id = 1;

        // when
        PostEntity result = postService.getPostById(id);

        // then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getWriter().getEmail()).isEqualTo("test@test.com");
    }

    @Test
    @DisplayName("PostCreateDto 를 이용하여 게시물 생성")
    void create_success() {
        // given
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .writerId(1)
                .content("test")
                .build();

        // when
        PostEntity result = postService.create(postCreateDto);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("test");
        assertThat(result.getCreatedAt()).isGreaterThan(0);
    }

    @Test
    @DisplayName("PostUpdateDto 를 이용하여 게시물 수정")
    void update_success() {
        // given
        PostUpdateDto userUpdateDto = PostUpdateDto.builder()
                .content("update content")
                .build();

        // when
        postService.update(1, userUpdateDto);

        // then
        PostEntity postEntity = postService.getPostById(1);
        assertThat(postEntity.getContent()).isEqualTo("update content");
        assertThat(postEntity.getModifiedAt()).isGreaterThan(0);
    }

}