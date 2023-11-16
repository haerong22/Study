package com.example.test_demo.user.controller;

import com.example.test_demo.mock.TestClockHolder;
import com.example.test_demo.mock.TestContainer;
import com.example.test_demo.mock.TestUuidHolder;
import com.example.test_demo.user.controller.response.UserResponse;
import com.example.test_demo.user.domain.UserCreate;
import com.example.test_demo.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateControllerTest {

    @Test
    @DisplayName("회원 가입을 할 수 있고 회원가입된 사용자는 PENDING 상태이다.")
    void createUser_201() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new TestClockHolder(1678530673958L))
                .uuidHolder(new TestUuidHolder("1234-1234-1234-1234"))
                .build();

        UserCreate userCreate = UserCreate.builder()
                .email("testtest@test.com")
                .nickname("bobby")
                .address("seoul")
                .build();

        // when
        ResponseEntity<UserResponse> result = testContainer.userCreateController.createUser(userCreate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("testtest@test.com");
        assertThat(result.getBody().getNickname()).isEqualTo("bobby");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getBody().getLastLoginAt()).isNull();
        assertThat(testContainer.userRepository.findById(1).isEmpty()).isFalse();
        assertThat(testContainer.userRepository.findById(1).get().getCertificationCode()).isEqualTo("1234-1234-1234-1234");
    }
}