package org.example.delivery.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.common.error.CommonErrorCode;
import org.example.delivery.common.exception.ApiException;
import org.example.delivery.api.domain.token.interfaces.TokenHelper;
import org.example.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenHelper tokenHelper;

    public TokenDto issueAccessToken(Long userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        return tokenHelper.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        return tokenHelper.issueRefreshToken(data);
    }

    public Long validationToken(String token) {
        Map<String, Object> map = tokenHelper.validationTokenWithThrow(token);
        Object userId = map.get("userId");
        Objects.requireNonNull(userId, () -> {throw new ApiException(CommonErrorCode.NULL_POINT);});
        return Long.parseLong(userId.toString());
    }
}
