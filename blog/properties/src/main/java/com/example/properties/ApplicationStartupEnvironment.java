package com.example.properties;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//@Component
public class ApplicationStartupEnvironment implements ApplicationListener<ApplicationStartedEvent> {

    private final Environment env;

    public ApplicationStartupEnvironment(Environment env) {
        this.env = env;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        String username = env.getProperty("test.username");
        String password = env.getProperty("test.password");
        System.out.println("username = " + username);
        System.out.println("password = " + password);
    }
}
