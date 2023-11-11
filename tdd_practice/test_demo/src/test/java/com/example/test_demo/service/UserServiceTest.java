package com.example.test_demo.service;

import com.example.test_demo.exception.CertificationCodeNotMatchedException;
import com.example.test_demo.exception.ResourceNotFoundException;
import com.example.test_demo.model.UserStatus;
import com.example.test_demo.model.dto.UserCreateDto;
import com.example.test_demo.model.dto.UserUpdateDto;
import com.example.test_demo.repository.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doNothing;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private JavaMailSender mailSender;

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

    @Test
    @DisplayName("UserCreateDto 를 이용하여 유저 생성")
    void create_success() {
        // given
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .email("test3@test.com")
                .address("jeju")
                .nickname("bobby3")
                .build();

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // when
        UserEntity result = userService.create(userCreateDto);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    @DisplayName("UserUpdateDto 를 이용하여 유저 데이터 수정")
    void update_success() {
        // given
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .address("incheon")
                .nickname("bobby4")
                .build();

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // when
        userService.update(1, userUpdateDto);

        // then
        UserEntity userEntity = userService.getById(1);
        assertThat(userEntity.getAddress()).isEqualTo("incheon");
        assertThat(userEntity.getNickname()).isEqualTo("bobby4");
    }

    @Test
    @DisplayName("로그인 하면 마지막 로그인 시간이 변경")
    void login_success() {
        // given

        // when
        userService.login(1);

        // then
        UserEntity userEntity = userService.getById(1);
        assertThat(userEntity.getLastLoginAt()).isGreaterThan(0L);
    }

    @Test
    @DisplayName("PENDING 상태의 사용자는 인증 코드로 ACTIVE 가능")
    void verify_email_success() {
        // given

        // when
        userService.verifyEmail(2, "4321-1234-1234-1234");

        // then
        UserEntity userEntity = userService.getById(2);
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    @DisplayName("PENDING 상태의 사용자는 잘못된 인증 코드로 인증 시도 시 에러")
    void verify_email_fail() {
        // given

        // when

        // then
        assertThatThrownBy(() -> {
            userService.verifyEmail(2, "");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }


}