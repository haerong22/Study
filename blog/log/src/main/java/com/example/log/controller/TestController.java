package com.example.log.controller;

import com.example.log.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @Autowired
    TestService testService;

    Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/log")
    public String test() {
        log.trace("controller trace log");
        log.debug("controller debug log");
        log.info("controller info log");
        log.warn("controller warn log");
        log.error("controller error log");
        testService.test();
        return "success";
    }
}
