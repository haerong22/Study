package com.example.restcontroller.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StartController {

    @RequestMapping(value = "/first-url", method = RequestMethod.GET)
    public void chapter1_1() {
    }

    @ResponseBody
    @RequestMapping("/helloworld")
    public String chapter1_2() {
        return "hello world";
    }

    @ResponseBody
    @GetMapping("/hello-spring")
    public String chapter1_3() {
        return "hello spring";
    }
}
