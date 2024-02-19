package com.example.restcontroller.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.user.entity.User;
import com.example.restcontroller.user.exception.PasswordNotMatchException;
import com.example.restcontroller.user.model.UserLoginToken;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@UtilityClass
public class JWTUtils {

    private static final String KEY = "kim";
    private static final String CLAIM_USER_ID = "user_id";

    public static Optional<UserLoginToken> createToken(User user) {

        String token = JWT.create()
                .withExpiresAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)))
                .withClaim(CLAIM_USER_ID, user.getId())
                .withSubject(user.getEmail())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512("kim".getBytes()));

        return Optional.of(UserLoginToken.builder()
                .token(token)
                .build());
    }

    public static String getIssuer(String token) {
        return JWT.require(Algorithm.HMAC512(KEY.getBytes()))
                    .build()
                    .verify(token)
                    .getIssuer();
    }
}
