package com.chat.websocket.config

import com.chat.websocket.handler.ChatWebSocketHandler
import com.chat.websocket.interceptor.WebSocketHandshakeInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val chatWebSocketHandler: ChatWebSocketHandler,
    private val webSocketHandshakeInterceptor: WebSocketHandshakeInterceptor,
) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry
            .addHandler(chatWebSocketHandler, "/ws/chat")
            .addInterceptors(webSocketHandshakeInterceptor)
            .setAllowedOrigins("*")
    }
}