package com.example.jpablog.controller.api;

import com.example.jpablog.dto.ResponseDto;
import com.example.jpablog.model.RoleType;
import com.example.jpablog.model.User;
import com.example.jpablog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {

    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/user")
    public ResponseDto<Integer> save(@RequestBody User user) {
        user.setRole(RoleType.USER);
        int result = userService.회원가입(user);
        return new ResponseDto<>(result, HttpStatus.OK.value());
    }

    @PostMapping("/user/login")
    public ResponseDto<Integer> login(@RequestBody User user) {
        User principal = userService.로그인(user);
        if (principal != null) {
            session.setAttribute("principal", principal);
        }
        return new ResponseDto<>(1, HttpStatus.OK.value());
    }
}
