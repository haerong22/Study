package org.example.websocketspringgraphql.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.websocketspringgraphql.entity.Message;
import org.example.websocketspringgraphql.entity.MessageEvent;
import org.springframework.context.event.EventListener;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;

@Controller
@Slf4j
public class SubscriptionController {

    private final Sinks.Many<Message> sink;

    public SubscriptionController() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @EventListener
    public void onMessageEvent(MessageEvent event) {
        log.info("Message received: {}", event.getMessage());
        Sinks.EmitResult result = sink.tryEmitNext(event.getMessage());
        if (result.isFailure()) {
            // 실패 시 로깅을 수행하고 필요한 경우 복구 메커니즘을 실행할 수 있습니다.
            log.error("Failed to emit message: {}", result);
        }
    }

    @SubscriptionMapping
    public Flux<Message> messagePosted() {
        return Flux.create(emitter -> {
            Consumer<Message> messageConsumer = message -> {
                if (!emitter.isCancelled()) {
                    emitter.next(message);
                }
            };

            this.sink.asFlux().subscribe(messageConsumer, emitter::error, emitter::complete);
        }, FluxSink.OverflowStrategy.BUFFER);
    }
}
