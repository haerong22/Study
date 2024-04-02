package com.example.webflux.sse.controller;

import com.example.webflux.sse.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private static final AtomicInteger lastEventId = new AtomicInteger(1);

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getNotifications() {
        return notificationService.getMessageFromSink()
                .map(msg -> ServerSentEvent
                        .builder(msg)
                        .id(lastEventId.getAndIncrement() + "")
                        .comment("this is notification")
                        .build()
                );
    }

    @PostMapping
    public Mono<String> addNotification(@RequestBody Event event) {
        String message = event.getType() + ": " + event.getMessage();
        notificationService.tryEmitNext(message);
        return Mono.just("ok");
    }
}
