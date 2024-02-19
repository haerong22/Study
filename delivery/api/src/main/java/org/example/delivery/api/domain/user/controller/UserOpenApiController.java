package org.example.delivery.api.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.delivery.api.common.api.Api;
import org.example.delivery.api.domain.token.controller.model.TokenResponse;
import org.example.delivery.api.domain.user.business.UserBusiness;
import org.example.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.example.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.example.delivery.api.domain.user.controller.model.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    /**
     * 사용자 가입 요청
     */
    @PostMapping
    public Api<UserResponse> register(
            @Valid @RequestBody UserRegisterRequest request
    ) {
        return Api.ok(userBusiness.register(request));
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public Api<TokenResponse> login(
            @Valid @RequestBody UserLoginRequest request
    ) {
        return Api.ok(userBusiness.login(request));
    }
}
