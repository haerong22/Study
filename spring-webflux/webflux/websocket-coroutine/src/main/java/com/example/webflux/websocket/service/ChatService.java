package com.example.webflux.websocket.service;

import com.example.webflux.websocket.entity.ChatDocument;
import com.example.webflux.websocket.repository.ChatMongoRepository;
import com.mongodb.client.model.changestream.OperationType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatMongoRepository chatMongoRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private static final Map<String, Sinks.Many<Chat>> chatSinkMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void setup() {
        reactiveMongoTemplate.changeStream(ChatDocument.class)
                .listen()
                .doOnNext(item -> {
                    ChatDocument target = item.getBody();
                    OperationType operationType = item.getOperationType();

                    log.info("target: {}", target);
                    log.info("type: {}", operationType);

                    if (target != null && operationType == OperationType.INSERT) {
                        String from = target.getFrom();
                        String to = target.getTo();
                        String message = target.getMessage();
                        doSend(from, to, message);
                    }
                })
                .subscribe();
    }

    public Flux<Chat> register(String iam) {
        Sinks.Many<Chat> sink = Sinks.many().unicast().onBackpressureBuffer();
        chatSinkMap.put(iam, sink);
        return sink.asFlux();
    }

    public void sendChat(String from, String to, String message) {
        var chatDocument = new ChatDocument(from, to, message);
        chatMongoRepository.save(chatDocument)
                .subscribe();
    }

    private void doSend(String from, String to, String message) {
        Sinks.Many<Chat> sink = chatSinkMap.get(to);

        if (sink == null) {
            Sinks.Many<Chat> my = chatSinkMap.get(from);
            my.tryEmitNext(new Chat("대화 상대가 없습니다.", "system"));
            return;
        }

        sink.tryEmitNext(new Chat(message, from));
    }
}
