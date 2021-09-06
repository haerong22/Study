package com.example.dispatcherservlet;

public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
