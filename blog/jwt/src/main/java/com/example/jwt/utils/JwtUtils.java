package com.example.jwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.ECGenParameterSpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtUtils {

    private JwtUtils() {}

    public static KeyPair generateES256Keypair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
            generator.initialize(new ECGenParameterSpec("secp256r1"));
            return generator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuer("k")
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusDays(1L).atZone(ZoneId.systemDefault()).toInstant()))
                .withClaim("username", username)
                .sign(algorithm);
    }

    public static boolean verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        try {
            DecodedJWT verify = JWT.require(algorithm)
                    .build().verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
