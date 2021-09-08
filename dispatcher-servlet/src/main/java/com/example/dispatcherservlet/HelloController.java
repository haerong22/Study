package com.example.dispatcherservlet;

import com.example.dispatcherservlet.annotation.Controller;
import com.example.dispatcherservlet.annotation.RequestMapping;
import com.example.dispatcherservlet.annotation.ResponseBody;

@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/add")
    public String add() {
        return "add";
    }
}
