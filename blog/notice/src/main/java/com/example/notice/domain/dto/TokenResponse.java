package com.example.notice.domain.dto;

import lombok.Data;

@Data
public class TokenResponse {

    private String tokenType;
    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;
    private Integer refreshTokenExpiresIn;
}
