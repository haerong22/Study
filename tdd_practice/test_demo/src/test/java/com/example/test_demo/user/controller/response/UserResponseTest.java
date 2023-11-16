package com.example.test_demo.user.controller.response;

import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserResponseTest {

    @Test
    @DisplayName("User로 응답을 생성한다.")
    void userResponse_test() {
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
        UserResponse userResponse = UserResponse.from(user);

        // then
        assertThat(userResponse.getId()).isEqualTo(1);
        assertThat(userResponse.getEmail()).isEqualTo("test@test.com");
        assertThat(userResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(userResponse.getLastLoginAt()).isEqualTo(100);
    }
}