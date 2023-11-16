package com.example.test_demo.user.domain;

import com.example.test_demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.test_demo.mock.TestClockHolder;
import com.example.test_demo.mock.TestUuidHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @Test
    @DisplayName("UserCreate 객체로 생성한다.")
    void userCreate_test() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("test@test.com")
                .nickname("bobby")
                .address("seoul")
                .build();

        // when
        User user = User.from(userCreate, new TestUuidHolder("1234-1234-1234-1234"));

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getNickname()).isEqualTo("bobby");
        assertThat(user.getAddress()).isEqualTo("seoul");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("1234-1234-1234-1234");
    }

    @Test
    @DisplayName("UserUpdate 객체로 데이터를 업데이트 한다.")
    void userUpdate_test() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("bobby")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("1234-1234-1234-1234")
                .build();

        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("bobby2")
                .address("busan")
                .build();

        // when
        user = user.update(userUpdate);

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getNickname()).isEqualTo("bobby2");
        assertThat(user.getAddress()).isEqualTo("busan");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("1234-1234-1234-1234");
        assertThat(user.getLastLoginAt()).isEqualTo(100);

    }

    @Test
    @DisplayName("로그인 시 마지막 로그인 시간이 변경된다.")
    void login_success() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("bobby")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("1234-1234-1234-1234")
                .build();

        // when
        user = user.login(new TestClockHolder(1678530673958L));

        // then
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);

    }

    @Test
    @DisplayName("유효한 인증 코드로 계정을 활성화 한다.")
    void certification_success() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("bobby")
                .address("seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("1234-1234-1234-1234")
                .build();

        // when
        user = user.certificate("1234-1234-1234-1234");

        // then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);

    }

    @Test
    @DisplayName("잘못된 인증 코드로 계정 활성화를 시도하면 에러가 발생한다.")
    void certification_fail() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("bobby")
                .address("seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("1234-1234-1234-1234")
                .build();

        // when

        // then
        assertThatThrownBy(() -> {
            user.certificate("");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);

    }
}