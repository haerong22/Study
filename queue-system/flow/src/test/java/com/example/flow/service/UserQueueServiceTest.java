package com.example.flow.service;

import com.example.flow.EmbeddedRedis;
import com.example.flow.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@SpringBootTest
@Import(EmbeddedRedis.class)
@ActiveProfiles("test")
class UserQueueServiceTest {

    @Autowired
    private UserQueueService userQueueService;

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @BeforeEach
    public void beforeEach() {
        ReactiveRedisConnection redisConnection = reactiveRedisTemplate.getConnectionFactory().getReactiveConnection();
        redisConnection.serverCommands().flushAll().subscribe();
    }


    @DisplayName("대기열을 등록하면 대기번호를 응답한다.")
    @Test
    void registerWaitQueueTest() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L))
                .expectNext(1L)
                .verifyComplete();

        StepVerifier.create(userQueueService.registerWaitQueue("default", 101L))
                .expectNext(2L)
                .verifyComplete();

        StepVerifier.create(userQueueService.registerWaitQueue("default", 102L))
                .expectNext(3L)
                .verifyComplete();
    }

    @DisplayName("대기열 등록 시 이미 등록 된 userId인 경우 에러를 응답한다.")
    @Test
    void alreadyRegisterWaitQueueTest() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L))
                .expectNext(1L)
                .verifyComplete();

        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L))
                .expectError(ApplicationException.class)
                .verify();
    }


    @DisplayName("진입 요청 시 대기 유저가 없으면 0을 응답한다.")
    @Test
    void emptyAllowUser() {
        StepVerifier.create(userQueueService.allowUser("default", 3L))
                .expectNext(0L)
                .verifyComplete();
    }

    @DisplayName("진입 요청 시 허용된 유저 수를 응답한다.")
    @Test
    void allowUser() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L)
                        .then(userQueueService.registerWaitQueue("default", 101L))
                        .then(userQueueService.registerWaitQueue("default", 102L))
                        .then(userQueueService.allowUser("default", 2L)))
                .expectNext(2L)
                .verifyComplete();
    }

    @DisplayName("진입 요청 수 보다 대기 유저 수가 적으면 허용된 유저 수를 응답한다.")
    @Test
    void allowUser2() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L)
                        .then(userQueueService.registerWaitQueue("default", 101L))
                        .then(userQueueService.registerWaitQueue("default", 102L))
                        .then(userQueueService.allowUser("default", 5L)))
                .expectNext(3L)
                .verifyComplete();
    }

    @DisplayName("진입 허용이 되면 대기열에서 제거 된다.")
    @Test
    void allowUserAfterRegisterWaitQueue() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L)
                        .then(userQueueService.registerWaitQueue("default", 101L))
                        .then(userQueueService.registerWaitQueue("default", 102L))
                        .then(userQueueService.allowUser("default", 3L))
                        .then(userQueueService.registerWaitQueue("default", 200L))
                )
                .expectNext(1L)
                .verifyComplete();
    }

    @DisplayName("진입 가능 여부를 조회 한다.")
    @Test
    void isNotAllowed() {
        StepVerifier.create(userQueueService.isAllowed("default", 100L))
                .expectNext(false)
                .verifyComplete();
    }

    @DisplayName("진입 가능 여부를 조회 한다.")
    @Test
    void isNotAllowed2() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L)
                        .then(userQueueService.allowUser("default", 3L))
                        .then(userQueueService.isAllowed("default", 101L)))
                .expectNext(false)
                .verifyComplete();
    }

    @DisplayName("진입 가능 여부를 조회 한다.")
    @Test
    void isAllowed() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L)
                        .then(userQueueService.allowUser("default", 3L))
                        .then(userQueueService.isAllowed("default", 100L)))
                .expectNext(true)
                .verifyComplete();
    }
}