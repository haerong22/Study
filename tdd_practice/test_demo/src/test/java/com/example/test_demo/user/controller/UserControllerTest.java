package com.example.test_demo.user.controller;

import com.example.test_demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.test_demo.common.domain.exception.ResourceNotFoundException;
import com.example.test_demo.mock.TestClockHolder;
import com.example.test_demo.mock.TestContainer;
import com.example.test_demo.user.controller.response.MyProfileResponse;
import com.example.test_demo.user.controller.response.UserResponse;
import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserStatus;
import com.example.test_demo.user.domain.UserUpdate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserControllerTest {

    @Test
    @DisplayName("특정 유저의 개인정보를 제외한 정보를 응답받을 수 있다.")
    void getUserById_200() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        testContainer.userRepository.save(
                User.builder()
                        .id(1L)
                        .email("test@test.com")
                        .nickname("bobby")
                        .address("seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("1234-1234-1234-1234")
                        .lastLoginAt(100L)
                        .build()
        );

        // when
        ResponseEntity<UserResponse> result = testContainer.userController.getUserById(1);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("test@test.com");
        assertThat(result.getBody().getNickname()).isEqualTo("bobby");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(100);
    }

    @Test
    @DisplayName("존재하지 않는 유저의 아이디로 호출 시 404 응답 받는다.")
    void getUserById_404() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        // when

        // then
        assertThatThrownBy(() -> {
            testContainer.userController.getUserById(1);
        })
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Users에서 ID 1를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("인증 코드로 계정을 활성화할 수 있다.")
    void verifyEmail_302() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();

        testContainer.userRepository.save(
                User.builder()
                        .id(1L)
                        .email("test@test.com")
                        .nickname("bobby")
                        .address("seoul")
                        .status(UserStatus.PENDING)
                        .certificationCode("1234-1234-1234-1234")
                        .lastLoginAt(100L)
                        .build()
        );

        // when
        ResponseEntity<Void> result = testContainer.userController.verifyEmail(1, "1234-1234-1234-1234");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(302));
        assertThat(testContainer.userRepository.getById(1).getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(testContainer.userRepository.getById(1).getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    @DisplayName("인증 코드가 일치하지 않으면 403 응답을 받는다.")
    void verifyEmail_403() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();

        testContainer.userRepository.save(
                User.builder()
                        .id(1L)
                        .email("test@test.com")
                        .nickname("bobby")
                        .address("seoul")
                        .status(UserStatus.PENDING)
                        .certificationCode("1234-1234-1234-1234")
                        .lastLoginAt(100L)
                        .build()
        );

        // when

        // then
        assertThatThrownBy(() -> {
            testContainer.userController.verifyEmail(1, "");
        })
                .isInstanceOf(CertificationCodeNotMatchedException.class)
                .hasMessage("자격 증명에 실패하였습니다.");
    }

    @Test
    @DisplayName("내 정보를 불러올 때 주소를 받을 수 있다.")
    void getMyInfo_200() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();

        testContainer.userRepository.save(
                User.builder()
                        .id(1L)
                        .email("test@test.com")
                        .nickname("bobby")
                        .address("seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("1234-1234-1234-1234")
                        .lastLoginAt(100L)
                        .build()
        );

        // when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.getMyInfo("test@test.com");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("test@test.com");
        assertThat(result.getBody().getNickname()).isEqualTo("bobby");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getBody().getAddress()).isEqualTo("seoul");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    @DisplayName("잘못된 이메일로 내 정보를 불러올 때 404 응답을 받는다.")
    void getMyInfo_404() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();

        testContainer.userRepository.save(
                User.builder()
                        .id(1L)
                        .email("test@test.com")
                        .nickname("bobby")
                        .address("seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("1234-1234-1234-1234")
                        .lastLoginAt(100L)
                        .build()
        );

        // when

        // then
        assertThatThrownBy(() -> {
            testContainer.userController.getMyInfo("");
        })
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Users에서 ID 를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("내 정보를 수정할 수 있다.")
    void updateMyInfo_200() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();

        testContainer.userRepository.save(
                User.builder()
                        .id(1L)
                        .email("test@test.com")
                        .nickname("bobby")
                        .address("seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("1234-1234-1234-1234")
                        .lastLoginAt(100L)
                        .build()
        );

        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("new bobby")
                .address("busan")
                .build();

        // when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.updateMyInfo("test@test.com", userUpdate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("test@test.com");
        assertThat(result.getBody().getNickname()).isEqualTo("new bobby");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getBody().getAddress()).isEqualTo("busan");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(100);
    }
}