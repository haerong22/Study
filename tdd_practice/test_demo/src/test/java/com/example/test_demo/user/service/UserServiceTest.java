package com.example.test_demo.user.service;

import com.example.test_demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.test_demo.common.domain.exception.ResourceNotFoundException;
import com.example.test_demo.mock.FakeMailSender;
import com.example.test_demo.mock.FakeUserRepository;
import com.example.test_demo.mock.TestClockHolder;
import com.example.test_demo.mock.TestUuidHolder;
import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserCreate;
import com.example.test_demo.user.domain.UserStatus;
import com.example.test_demo.user.domain.UserUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    private UserServiceImpl userService;

    @BeforeEach
    void init() {
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        fakeUserRepository.save(
                User.builder()
                        .id(1L)
                        .email("test@test.com")
                        .nickname("bobby")
                        .address("seoul")
                        .certificationCode("1234-1234-1234-1234")
                        .status(UserStatus.ACTIVE)
                        .lastLoginAt(0L)
                        .build()
        );
        fakeUserRepository.save(
                User.builder()
                        .id(2L)
                        .email("test2@test.com")
                        .nickname("bobby2")
                        .address("busan")
                        .certificationCode("4321-1234-1234-1234")
                        .status(UserStatus.PENDING)
                        .lastLoginAt(0L)
                        .build()
        );

        this.userService = UserServiceImpl.builder()
                .userRepository(fakeUserRepository)
                .certificationService(new CertificationServiceImpl(new FakeMailSender()))
                .uuidHolder(new TestUuidHolder("1234-1234-1234-1234"))
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();
    }

    @Test
    @DisplayName("getByEmail 은 ACTIVE 상태인 유저 조회")
    void getByEmail_success() {
        // given
        String email = "test@test.com";

        // when
        User result = userService.getByEmail(email);

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
        User result = userService.getById(id);

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
    @DisplayName("UserCreate 를 이용하여 유저 생성")
    void create_success() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("test3@test.com")
                .address("jeju")
                .nickname("bobby3")
                .build();

        // when
        User result = userService.create(userCreate);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getCertificationCode()).isEqualTo("1234-1234-1234-1234");
    }

    @Test
    @DisplayName("UserUpdateDto 를 이용하여 유저 데이터 수정")
    void update_success() {
        // given
        UserUpdate userUpdate = UserUpdate.builder()
                .address("incheon")
                .nickname("bobby4")
                .build();

        // when
        userService.update(1, userUpdate);

        // then
        User user = userService.getById(1);
        assertThat(user.getAddress()).isEqualTo("incheon");
        assertThat(user.getNickname()).isEqualTo("bobby4");
    }

    @Test
    @DisplayName("로그인 하면 마지막 로그인 시간이 변경")
    void login_success() {
        // given

        // when
        userService.login(1);

        // then
        User user = userService.getById(1);
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    @DisplayName("PENDING 상태의 사용자는 인증 코드로 ACTIVE 가능")
    void verify_email_success() {
        // given

        // when
        userService.verifyEmail(2, "4321-1234-1234-1234");

        // then
        User user = userService.getById(2);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
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