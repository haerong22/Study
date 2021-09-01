package com.example.httpencryption.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class TestController {

    @GetMapping("/")
    public String hello(HttpServletRequest request) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        String s = new String(bytes, StandardCharsets.UTF_8);

        return s;
    }
}
