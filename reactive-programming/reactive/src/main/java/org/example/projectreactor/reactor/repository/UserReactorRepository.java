package org.example.projectreactor.reactor.repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.projectreactor.common.repository.UserEntity;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public class UserReactorRepository {
    private final Map<String, UserEntity> userMap;

    public UserReactorRepository() {
        var user = new UserEntity("1234", "bobby", 32, "image#1000");

        userMap = Map.of("1234", user);
    }

    @SneakyThrows
    public Mono<UserEntity> findById(String userId) {
        return Mono.create(sink -> {
            log.info("UserRepository.findById: {}", userId);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            UserEntity user = userMap.get(userId);
            if (user == null) {
                sink.success();
            } else {
                sink.success(user);
            }
        });

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        UserEntity user = userMap.get(userId);
//        return Mono.justOrEmpty(user);
    }
}