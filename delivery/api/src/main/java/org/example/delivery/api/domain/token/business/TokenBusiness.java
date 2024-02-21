package org.example.delivery.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.example.delivery.common.annotation.Business;
import org.example.delivery.common.error.CommonErrorCode;
import org.example.delivery.common.exception.ApiException;
import org.example.delivery.api.domain.token.controller.model.TokenResponse;
import org.example.delivery.api.domain.token.converter.TokenConverter;
import org.example.delivery.api.domain.token.model.TokenDto;
import org.example.delivery.api.domain.token.service.TokenService;
import org.example.delivery.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TokenBusiness {

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    public TokenResponse issueToken(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(UserEntity::getId)
                .map(userId -> {
                    TokenDto accessToken = tokenService.issueAccessToken(userId);
                    TokenDto refreshToken = tokenService.issueRefreshToken(userId);
                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    public Long validationAccessToken(String accessToken) {
        return tokenService.validationToken(accessToken);
    }
}
