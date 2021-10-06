package com.example.jwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.tomcat.util.codec.binary.Base64;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

class JwtUtilsTest {

    @Test
    @DisplayName("Generate keypair")
    void generate_keypair() {
        KeyPair keyPair = JwtUtils.generateES256Keypair();

        assert keyPair != null;
        System.out.println("keyPair.getPublic() = " + Base64.encodeBase64URLSafeString(keyPair.getPublic().getEncoded()));
        System.out.println("keyPair.getPrivate() = " + Base64.encodeBase64URLSafeString(keyPair.getPrivate().getEncoded()));
    }

    @Test
    @DisplayName("Generate token ES256")
    void generate_token_ES256() {
        KeyPair keyPair = JwtUtils.generateES256Keypair();

        assert keyPair != null;
        Algorithm algorithm = Algorithm.ECDSA256((ECPublicKey) keyPair.getPublic(), (ECPrivateKey) keyPair.getPrivate());
        String token = JWT.create()
                .withIssuer("k")
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusDays(1L).atZone(ZoneId.systemDefault()).toInstant()))
                .withClaim("username", "kim")
                .sign(algorithm);

        DecodedJWT decode = JWT.decode(token);

        String header = new String(Base64.decodeBase64(decode.getHeader()), StandardCharsets.UTF_8);
        String payload = new String(Base64.decodeBase64(decode.getPayload()), StandardCharsets.UTF_8);

        System.out.println("header = " + header);
        System.out.println("payload = " + payload);

        System.out.println("token = " + token);
    }

    @Test
    @DisplayName("Verify token ES256")
    void verify_token_ES256() {
        KeyPair keyPair = JwtUtils.generateES256Keypair();

        assert keyPair != null;
        Algorithm algorithm = Algorithm.ECDSA256((ECPublicKey) keyPair.getPublic(), (ECPrivateKey) keyPair.getPrivate());
        String token = JWT.create()
                .withIssuer("k")
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusDays(1L).atZone(ZoneId.systemDefault()).toInstant()))
                .withClaim("username", "kim")
                .sign(algorithm);

        DecodedJWT decodeToken = JWT.decode(token);

        DecodedJWT verify = JWT.require(algorithm)
                .withIssuer("k")
                .withClaim("username", "kim")
                .build().verify(token);
        String payload = new String(Base64.decodeBase64(verify.getPayload()), StandardCharsets.UTF_8);
        String decode = new String(Base64.decodeBase64(decodeToken.getPayload()), StandardCharsets.UTF_8);

        Assertions.assertThat(payload).isEqualTo(decode);
    }

    @Test
    @DisplayName("Generate token HS256")
    void generate_token_HS256() {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("k")
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusDays(1L).atZone(ZoneId.systemDefault()).toInstant()))
                .withClaim("username", "kim")
                .sign(algorithm);

        DecodedJWT decode = JWT.decode(token);

        String header = new String(Base64.decodeBase64(decode.getHeader()), StandardCharsets.UTF_8);
        String payload = new String(Base64.decodeBase64(decode.getPayload()), StandardCharsets.UTF_8);

        System.out.println("header = " + header);
        System.out.println("payload = " + payload);

        System.out.println("token = " + token);
    }

    @Test
    @DisplayName("Verify token HS256")
    void verify_token_HS256() {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("k")
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusDays(1L).atZone(ZoneId.systemDefault()).toInstant()))
                .withClaim("username", "kim")
                .sign(algorithm);

        DecodedJWT decodeToken = JWT.decode(token);

        DecodedJWT verify = JWT.require(algorithm)
                .build().verify(token);
        String payload = new String(Base64.decodeBase64(verify.getPayload()), StandardCharsets.UTF_8);
        String decode = new String(Base64.decodeBase64(decodeToken.getPayload()), StandardCharsets.UTF_8);

        Assertions.assertThat(payload).isEqualTo(decode);
    }
}