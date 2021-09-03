package com.example.httpencryption.controller;

import com.example.httpencryption.dto.TestDto;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class TestController {

    @CrossOrigin
    @PostMapping("/")
    public TestDto hello(HttpServletRequest request, @RequestBody TestDto dto) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        String s = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("s = " + s);

        return dto;
    }
}
