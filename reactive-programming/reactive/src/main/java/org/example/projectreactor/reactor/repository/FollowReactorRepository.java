package org.example.projectreactor.reactor.repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.projectreactor.common.repository.UserEntity;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class FollowReactorRepository {
    private final Map<String, Long> userFollowCountMap;

    public FollowReactorRepository() {
        userFollowCountMap = Map.of("1234", 1000L);
    }

    @SneakyThrows
    public Mono<Long> countByUserId(String userId) {
        return Mono.create(sink -> {
            log.info("FollowRepository.countByUserId: {}", userId);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            sink.success(userFollowCountMap.getOrDefault(userId, 0L));
        });
    }

    @SneakyThrows
    public Mono<Long> countWithContext() {
        return Mono.deferContextual(context -> {
            Optional<UserEntity> user = context.getOrEmpty("user");

            if (user.isEmpty()) throw new RuntimeException("user not found");

            return Mono.just(user.get().getId());
        }).flatMap(this::countByUserId);
    }
}