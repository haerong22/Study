package com.example.jpablog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 인증이 안된 사용자들이 들어오는 경로 /auth/**
// "/" 요청시 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }
}
