package com.example.jpablog.controller.api;

import com.example.jpablog.config.auth.PrincipalDetail;
import com.example.jpablog.config.auth.PrincipalDetailService;
import com.example.jpablog.dto.ResponseDto;
import com.example.jpablog.model.User;
import com.example.jpablog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        int result = userService.회원가입(user);
        return new ResponseDto<>(result, HttpStatus.OK.value());
    }

    @PutMapping("/user/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody User user, Principal principal) {
        if (principal.getName().equals(user.getUsername())) {
            userService.회원수정(id, user);
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseDto<>(1, HttpStatus.OK.value());
        }
        return new ResponseDto<>(-1, HttpStatus.BAD_REQUEST.value());
    }

    /*// 기본 로그인
    @PostMapping("/user/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
        User principal = userService.로그인(user);
        if (principal != null) {
            session.setAttribute("principal", principal);
        }
        return new ResponseDto<>(1, HttpStatus.OK.value());
    }*/
}
