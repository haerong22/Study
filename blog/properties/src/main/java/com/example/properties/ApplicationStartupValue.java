package com.example.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupValue implements ApplicationListener<ApplicationStartedEvent> {

    @Value("${test.username}")
    private String username;

    @Value("${test.password}")
    private int password;

    @Value("${test.email}")
    private String email;

    @Value("${test.address}")
    private String address;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("email = " + email);
        System.out.println("address = " + address);
    }
}
