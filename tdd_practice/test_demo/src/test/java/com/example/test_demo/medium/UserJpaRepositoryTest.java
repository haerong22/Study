package com.example.test_demo.medium;

import com.example.test_demo.user.domain.UserStatus;
import com.example.test_demo.user.infrastructure.UserEntity;
import com.example.test_demo.user.infrastructure.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = true)
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Sql("/sql/user-repository-test-data.sql")
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("findByIdAndStatus로 유저 데이터 조회")
    void findByIdAndStatus_success() {
        // given

        // when
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1L, UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("findByIdAndStatus로 유저 데이터 없으면 Optional empty 응답")
    void findByIdAndStatus_empty() {
        // given

        // when
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1L, UserStatus.PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("findByEmailAndStatus로 유저 데이터 조회")
    void findByEmailAndStatus_success() {
        // given

        // when
        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("test@test.com", UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("findByEmailAndStatus로 유저 데이터 없으면 Optional empty 응답")
    void findByEmailAndStatus_empty() {
        // given

        // when
        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("test@test.com", UserStatus.PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();
    }

}