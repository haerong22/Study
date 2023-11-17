package com.example.test_demo.user.service;

import com.example.test_demo.mock.FakeMailSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CertificationServiceTest {

    @Test
    @DisplayName("이메일 컨텐츠 생성 확인")
    void email_content_test() {
        // given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);

        // when
        certificationService.send("test@test.com", 1, "1234-1234-1234-1234");

        // then
        assertThat(fakeMailSender.email).isEqualTo("test@test.com");
        assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
        assertThat(fakeMailSender.content).isEqualTo("Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=1234-1234-1234-1234");
    }
}