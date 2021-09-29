package com.example.properties;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//@Component
public class ApplicationStartupCustom implements ApplicationListener<ApplicationStartedEvent> {

    private final TestProperties testProperties;

    public ApplicationStartupCustom(TestProperties testProperties) {
        this.testProperties = testProperties;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("username = " + testProperties.getUsername());
        System.out.println("password = " + testProperties.getPassword());
    }
}
