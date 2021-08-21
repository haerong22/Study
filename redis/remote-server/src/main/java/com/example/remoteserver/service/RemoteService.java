package com.example.remoteserver.service;

import com.example.remoteinterface.service.RemoteServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteService implements RemoteServiceInterface {

    @Override
    public String sayHello(String name) {
        log.info("run sayHello");
        return "hello " + name;
    }
}
