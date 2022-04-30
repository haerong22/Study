package com.example.enumparam.controller;

import com.example.enumparam.domain.Developer;
import com.example.enumparam.domain.Position;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/enum")
    public String queryString(@RequestParam Position position) {
        System.out.println("position = " + position);
        return "success";
    }

    @GetMapping("/obj")
    public String queryString(@ModelAttribute Developer dto) {
        System.out.println("dto = " + dto);
        return "success";
    }

    @PostMapping("/json")
    public String json(@RequestBody Developer dto) {
        System.out.println("dto = " + dto);
        return "success";
    }
}
