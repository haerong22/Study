package com.example.flow.service;

import com.example.flow.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserQueueService {

    private final String USER_QUEUE_WAIT_KEY = "users:queue:%s:wait";

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    // 대기열 등록
    public Mono<Long> registerWaitQueue(final String queue, final Long userId) {
        long unixTimestamp = Instant.now().getEpochSecond();

        return reactiveRedisTemplate.opsForZSet()
                .add(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString(), unixTimestamp)
                .filter(i -> i)
                .switchIfEmpty(Mono.error(ErrorCode.QUEUE_ALREADY_REGISTERED_USER.build()))
                .flatMap(i -> reactiveRedisTemplate.opsForZSet().rank(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString()))
                .map(i -> i >= 0 ? i + 1 : i);
    }
}
