package com.example.jwt.controller;

import com.example.jwt.entity.LoginReq;
import com.example.jwt.repository.TestRepository;
import com.example.jwt.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestRepository testRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(LoginReq loginReq) {
        return testRepository.login(loginReq) ?
                ResponseEntity.status(HttpStatus.OK).header("token", JwtUtils.generateToken(loginReq.getUsername())).body("success") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
