package com.example.dispatcherservlet;

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
}
