package com.example.test_demo.service;

import com.example.test_demo.exception.ResourceNotFoundException;
import com.example.test_demo.repository.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("getByEmail 은 ACTIVE 상태인 유저 조회")
    void getByEmail_success() {
        // given
        String email = "test@test.com";

        // when
        UserEntity result = userService.getByEmail(email);

        // then
        assertThat(result.getNickname()).isEqualTo("bobby");
    }

    @Test
    @DisplayName("getByEmail 은 PENDING 상태인 유저 조회 시 에러 발생")
    void getByEmail_error() {
        // given
        String email = "test2@test.com";

        // when
        // then
        assertThatThrownBy(() -> userService.getByEmail(email))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("getById 은 ACTIVE 상태인 유저 조회")
    void getById_success() {
        // given
        long id = 1;

        // when
        UserEntity result = userService.getById(id);

        // then
        assertThat(result.getNickname()).isEqualTo("bobby");
    }

    @Test
    @DisplayName("getById 은 PENDING 상태인 유저 조회 시 에러 발생")
    void getById_error() {
        // given
        long id = 2;

        // when
        // then
        assertThatThrownBy(() -> userService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

}