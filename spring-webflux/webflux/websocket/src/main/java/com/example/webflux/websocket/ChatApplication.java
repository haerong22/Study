package com.example.webflux.websocket;

import com.example.webflux.websocket.repository.ChatMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Slf4j
@SpringBootApplication
public class ChatApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Autowired
    private ChatMongoRepository chatMongoRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public void run(String... args) throws Exception {
//        reactiveMongoTemplate.changeStream(ChatDocument.class)
//                .listen()
//                .doOnNext(item -> {
//                    ChatDocument target = item.getBody();
//                    OperationType operationType = item.getOperationType();
//                    BsonValue token = item.getResumeToken();
//
//                    log.info("target: {}", target);
//                    log.info("type: {}", operationType);
//                    log.info("token: {}", token);
//                })
//                .subscribe();
//
//        Thread.sleep(1000);
//
//        var newChat = new ChatDocument("a", "b", "hello");
//
//        chatMongoRepository.save(newChat)
//                .doOnNext(saved -> log.info("saved document: {}", saved))
//                .subscribe();
    }
}
