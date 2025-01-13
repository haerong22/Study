package com.example.webflux.websocket.handler;

import com.example.webflux.websocket.service.Chat;
import com.example.webflux.websocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler implements WebSocketHandler {

    private final ChatService chatService;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String iam = (String) session.getAttributes().get("iam");

        Flux<Chat> chatFlux = chatService.register(iam);
        chatService.sendChat("system", iam, iam + "님 채팅방에 오신 것을 환영합니다.");

        session.receive()
                .doOnNext(webSocketMessage -> {
                    String payload = webSocketMessage.getPayloadAsText();
                    log.info("payload: {}", payload);

                    String[] splits = payload.split(":");
                    String to = splits[0].trim();
                    String message = splits[1].trim();

                    chatService.sendChat(iam, to, message);
                }).subscribe();

        return session.send(
                chatFlux.map(chat -> session.textMessage(chat.from() + ": " + chat.message()))
        );
    }
}
