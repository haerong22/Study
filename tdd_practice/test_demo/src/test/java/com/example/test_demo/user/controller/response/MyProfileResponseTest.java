package com.example.test_demo.user.controller.response;

import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MyProfileResponseTest {

    @Test
    @DisplayName("User로 응답을 생성한다.")
    void myProfileResponse_test() {
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
        MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

        // then
        assertThat(myProfileResponse.getId()).isEqualTo(1);
        assertThat(myProfileResponse.getEmail()).isEqualTo("test@test.com");
        assertThat(myProfileResponse.getAddress()).isEqualTo("seoul");
        assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100);
    }
}