package com.example.jpablog.controller;

import com.example.jpablog.config.auth.PrincipalDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    // 파라미터로 시큐리티 세션 접근 : @AuthenticationPrincipal PrincipalDetail principal
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
