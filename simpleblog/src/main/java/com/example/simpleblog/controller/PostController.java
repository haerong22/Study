package com.example.simpleblog.controller;

import com.example.simpleblog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public PostCreate post(@RequestBody @Valid PostCreate params) {
        return params;
    }
}
