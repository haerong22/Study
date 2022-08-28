package org.example.passwordvalidator;

import org.example.passwordvalidator.PasswordValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

/**
 * 비밀번호는 최소 8자 이상 12자 이하
 * 비밀번호가 8자 미만 또는 12자 초과인 경우 IllegalArgumentException 발생
 * 경계조건에 대한 테스트 코드 작성
 */
@DisplayName("비밀번호 검증 테스트")
public class PasswordValidatorTest {

    @DisplayName("비밀번호가 최소 8자 이상 12자 이하면 예외가 발생하지 않는다.")
    @Test
    void validatePasswordTest() {

        assertThatCode(() -> PasswordValidator.validate("password123"))
                .doesNotThrowAnyException();
    }

    @DisplayName("비밀번호가 8자 미만 또는 12자 초과면 IllegalArgumentException 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1234567", "asdf1234567890"})
    void validatePasswordTest2(String password) {

        assertThatCode(() -> PasswordValidator.validate(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 최소 8자 이상 12자 이하여야 한다.");
    }
}
