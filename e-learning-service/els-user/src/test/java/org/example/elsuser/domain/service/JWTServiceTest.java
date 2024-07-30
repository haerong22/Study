package org.example.elsuser.domain.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.elsuser.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private JWTService jwtService;
    private final String secretKey = "1ad35d74dc65cc0ee9a3a409f7f87e332aa6aa89d9cc64c66cdf9a1ffdc534ee";

    @BeforeEach
    void setUp() {
        jwtService = new JWTService(passwordEncoder);
    }

    @Test
    void generate_token_valid_credentials_return_token() {
        // given
        User user = new User();
        user.setEmail("user@example.com");
        user.setPasswordHash("hashedPassword");

        given(passwordEncoder.matches("validPassword", user.getPasswordHash())).willReturn(true);

        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);

        // when
        String token = jwtService.generateToken(user, "validPassword");

        // then
        assertThat(token).isNotNull();

        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertThat(claims.getSubject()).isEqualTo(user.getEmail());
    }

    @Test
    void generate_token_invalid_credentials_throws_exception() {
        // given
        User user = new User();
        user.setEmail("user@example.com");
        user.setPasswordHash("hashedPassword");

        given(passwordEncoder.matches("invalidPassword", user.getPasswordHash())).willReturn(false);

        // when, then
        assertThatThrownBy(() -> jwtService.generateToken(user, "invalidPassword"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validate_token_validToken_return_true() {
        // given
        String subject = "user@example.com";
        long currentTimeMillis = System.currentTimeMillis();

        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);

        String token = Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + 3600000)) // Token expires in 1 hour
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();

        // when
        boolean result = jwtService.validateToken(token);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void validate_token_invalid_token_return_false() {
        // given
        String token = "invalid.token.here";

        // when
        boolean result = jwtService.validateToken(token);

        // then
        assertThat(result).isFalse();
    }
}