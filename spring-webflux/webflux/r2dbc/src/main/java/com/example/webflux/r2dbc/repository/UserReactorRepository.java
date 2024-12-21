package com.example.webflux.r2dbc.repository;

import com.example.webflux.r2dbc.entity.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@Slf4j
public class UserReactorRepository {
    private final Map<String, UserEntity> userMap;

    public UserReactorRepository() {
        var user = new UserEntity(
                "1",
                "bobby",
                10,
                "1",
                "1234"
        );

        userMap = Map.of("1", user);
    }

    @SneakyThrows
    public Mono<UserEntity> findById(String userId) {
        return Mono.create(sink -> {
            log.info("UserRepository.findById: {}", userId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                sink.error(new RuntimeException(e));
                return;
            }
            UserEntity user = userMap.get(userId);
            if (user == null) {
                sink.success();
            } else {
                sink.success(user);
            }
        });
    }
}