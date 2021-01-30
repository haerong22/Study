package com.example.jpablog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    // 파라미터로 시큐리티 세션 접근 : @AuthenticationPrincipal PrincipalDetail principal
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String save() {
        return "board/saveForm";
    }
}
