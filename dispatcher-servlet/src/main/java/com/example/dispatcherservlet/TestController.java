package com.example.dispatcherservlet;

import com.example.dispatcherservlet.annotation.Controller;
import com.example.dispatcherservlet.annotation.RequestMapping;
import com.example.dispatcherservlet.annotation.ResponseBody;

@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @ResponseBody
    @RequestMapping("/hi")
    public String hi() {
        return "hi";
    }

    @RequestMapping("/main")
    public String main() {
        return "main.jsp";
    }
}
