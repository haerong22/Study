package com.spring.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {


    @GetMapping({"", "/"})
    public String index() {
        // 머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정 : template (prefix), .mustache (suffix) -> 생략가능
        return "index";
    }

    @ResponseBody
    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @ResponseBody
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @ResponseBody
    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @ResponseBody
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @ResponseBody
    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @ResponseBody
    @GetMapping("/joinProc")
    public String joinProc() {
        return "회원가입 완료!";
    }
}
