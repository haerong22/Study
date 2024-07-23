package com.example.demo.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/playground/**")
                .addResourceLocations("classpath:/static/playground/");

        registry.addResourceHandler("/voyager/**")
                .addResourceLocations("classpath:/static/voyager/");
    }
}
