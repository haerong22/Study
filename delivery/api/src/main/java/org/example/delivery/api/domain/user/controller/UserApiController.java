package org.example.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.delivery.api.common.annotation.UserSession;
import org.example.delivery.api.common.api.Api;
import org.example.delivery.api.domain.user.business.UserBusiness;
import org.example.delivery.api.domain.user.controller.model.UserResponse;
import org.example.delivery.api.domain.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me(@UserSession User user) {
        return Api.ok(userBusiness.me(user));
    }
}
