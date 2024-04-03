package com.example.webflux.websocket.config;

import com.example.webflux.websocket.handler.ChatWebSocketHandler;
import com.example.webflux.websocket.handler.SimpleWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.Map;

@Configuration
public class MappingConfig {

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(
            SimpleWebSocketHandler simpleWebSocketHandler,
            ChatWebSocketHandler chatWebSocketHandler
    ) {
        Map<String, WebSocketHandler> urlMapper = Map.of(
                "/simple", simpleWebSocketHandler,
                "/chat", chatWebSocketHandler
        );

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(1);
        mapping.setUrlMap(urlMapper);

        return mapping;
    }
}
