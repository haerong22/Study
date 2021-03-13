package com.example.restcontroller.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.user.exception.PasswordNotMatchException;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;

@UtilityClass
public class JWTUtils {

    private final String KEY = "kim";

    public static String getIssuer(String token) {
        return JWT.require(Algorithm.HMAC512(KEY.getBytes()))
                    .build()
                    .verify(token)
                    .getIssuer();
    }
}
