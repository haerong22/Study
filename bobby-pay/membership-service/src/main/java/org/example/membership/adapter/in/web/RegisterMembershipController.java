package org.example.membership.adapter.in.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterMembershipController {

    @GetMapping("/test")
    public void test() {
        System.out.println("Hello world!");
    }
}
