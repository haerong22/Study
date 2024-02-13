package org.example.delivery.api.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.delivery.api.common.api.Api;
import org.example.delivery.api.domain.user.business.UserBusiness;
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
        UserResponse response = userBusiness.register(request);
        return Api.ok(response);
    }
}
