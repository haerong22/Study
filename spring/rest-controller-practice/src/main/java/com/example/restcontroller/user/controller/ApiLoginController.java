package com.example.restcontroller.user.controller;

import com.example.restcontroller.common.exception.BizException;
import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.notice.model.ResponseError;
import com.example.restcontroller.user.entity.User;
import com.example.restcontroller.user.model.UserLogin;
import com.example.restcontroller.user.model.UserLoginToken;
import com.example.restcontroller.user.service.UserService;
import com.example.restcontroller.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ApiLoginController {

    private final UserService userService;

    @PostMapping("/api/login")
    public ResponseEntity<?> chapter4_1(@RequestBody @Valid UserLogin userLogin, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseResult.fail("입력값이 정확하지 않습니다.", ResponseError.of(bindingResult.getFieldErrors()));
        }
        User user = userService.login(userLogin);

        UserLoginToken userLoginToken = JWTUtils.createToken(user)
                .orElseThrow(() -> new BizException("JWT 생성에 실패하였습니다."));

        return ResponseResult.success(userLoginToken);
    }
}