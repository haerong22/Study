package com.example.notice.controller;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class UserController {

    @GetMapping("/login")
    public Object login() {
        return "login";
    }

    @GetMapping("/oauth")
    public String login(@RequestParam("code") String code) {
        String url = "https://kauth.kakao.com/oauth/token";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<>
        restTemplate.postForEntity();

        return "index";
    }
}
