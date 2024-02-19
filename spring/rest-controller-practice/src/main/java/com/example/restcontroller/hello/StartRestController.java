package com.example.restcontroller.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartRestController {

    @GetMapping("/hello-rest")
    public String chapter1_4() {
        return "\"hello rest\"";
    }

    @GetMapping("/api/helloworld")
    public String chapter1_5() {
        return "hello rest api";
    }
}
