package com.example.jpablog.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class RestControllerTest {

    @GetMapping("/http/get")
    public String getTest(Member member) {
        return "get 요청" + member.getId() + ", " + member.getUsername();
    }

    @PostMapping("/http/post")
    public ResponseEntity<Member> postTest(@RequestBody Member member) {
        return new ResponseEntity<>(member, HttpStatus.OK);
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
