package com.example.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void ConfigureGlobal(AuthenticationManagerBuilder auth)
        throws Exception{
        auth.inMemoryAuthentication()
                .withUser("kim")
                .password("{noop}test1234")
                .roles("USER");
    }
}
