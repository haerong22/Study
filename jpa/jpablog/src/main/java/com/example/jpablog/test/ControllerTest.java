package com.example.jpablog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerTest {

    @GetMapping("/temp/home")
    public String tempHome() {
        // 파일 리턴 기본경로 : src/main/resources/static
        return "/home.html";
    }

    @GetMapping("/temp/jsp")
    public String tempJsp() {
        return "/test";
    }
}
