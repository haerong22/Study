package com.example.webflux.sse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.ReactiveStreamOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.stream.StreamReceiver;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.Map;

@Slf4j
@Service
public class NotificationService {
    private static final Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();
    private static final String STREAM_NAME = "notification:1";

    private final ReactiveStreamOperations<String, String, String> streamOperations;
    private final StreamReceiver<String, MapRecord<String, String, String>> streamReceiver;

    public NotificationService(
            ReactiveStringRedisTemplate redisTemplate,
            ReactiveRedisConnectionFactory redisConnectionFactory
    ) {
        this.streamOperations = redisTemplate.opsForStream();

        var options = StreamReceiver.StreamReceiverOptions.builder()
                .pollTimeout(Duration.ofMillis(100L))
                .build();
        this.streamReceiver = StreamReceiver.create(redisConnectionFactory, options);

        var streamOffset = StreamOffset.create(STREAM_NAME, ReadOffset.latest());
        streamReceiver.receive(streamOffset)
                .subscribe(record -> {
                    log.info("record: {}", record);
                    var values = record.getValue();
                    var message = values.get("message");
                    sink.tryEmitNext(message);
                });
    }

    public Flux<String> getMessageFromSink() {
        return sink.asFlux();
    }

    public void tryEmitNext(String message) {
        log.info("message = {}", message);
        streamOperations.add(STREAM_NAME, Map.of("message", message))
                        .subscribe();
    }
}
