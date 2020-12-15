package com.security.jwt.config.jwt;

public interface JwtProperties {
    String SECRET = "wj";
    int EXPIRATION_TIME = 60000*10;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
