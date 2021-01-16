package com.example.reflect.controller;

import com.example.reflect.anno.RequestMapping;
import com.example.reflect.controller.dto.JoinDto;
import com.example.reflect.controller.dto.LoginDto;

public class UserController {

    @RequestMapping("/user/join")
    public String join(JoinDto dto){
        System.out.println("join() 함수 호출");
        return "/";
    }

    @RequestMapping("/user/login")
    public String login(LoginDto dto){
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
