package com.example.reflect.controller;

import com.example.reflect.anno.RequestMapping;

public class UserController {

    @RequestMapping("/join")
    public String join(){
        System.out.println("join() 함수 호출");
        return "/";
    }

    @RequestMapping("/login")
    public String login(){
        System.out.println("login()함수 호출");
        return "/";
    }

    @RequestMapping("/user")
    public String user(){
        System.out.println("user()함수 호출");
        return "/";
    }

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("hello()함수 호출");
        return "/";
    }
}
