package com.example.jpablog.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class HttpControllerTest {

    @GetMapping("/http/get")
    public String getTest(Member member) {
        return "get 요청" + member.getId() + ", " + member.getUsername();
    }

    @PostMapping("/http/post")
    public String postTest() {
        return "post 요청";
    }

    @PutMapping("/http/put")
    public String putTest() {
        return "put 요청";
    }

    @DeleteMapping("http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
