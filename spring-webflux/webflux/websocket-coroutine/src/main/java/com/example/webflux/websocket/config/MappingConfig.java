package com.example.webflux.websocket.config;

import com.example.webflux.websocket.handler.ChatCoroutineWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import java.util.Map;

@Configuration
public class MappingConfig {

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(
            ChatCoroutineWebSocketHandler chatWebSocketHandler
    ) {
        Map<String, ChatCoroutineWebSocketHandler> urlMapper = Map.of(
                "/chat", chatWebSocketHandler
        );

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(1);
        mapping.setUrlMap(urlMapper);

        return mapping;
    }
}
