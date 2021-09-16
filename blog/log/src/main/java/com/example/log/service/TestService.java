package com.example.log.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    Logger log = LoggerFactory.getLogger(TestService.class);

    public void test() {
        log.trace("service trace log");
        log.debug("service debug log");
        log.info("service info log");
        log.warn("service warn log");
        log.error("service error log");
    }
}
