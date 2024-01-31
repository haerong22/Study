package com.example.webflux.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//@Repository
public class UserRepositoryImpl implements UserRepository {

    private final ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Mono<User> save(User user) {
        LocalDateTime now = LocalDateTime.now();

        if (user.getId() == null) {
            user.setId(sequence.getAndAdd(1));
            user.setCreatedAt(now);
        }

        user.setUpdatedAt(now);
        users.put(user.getId(), user);

        return Mono.just(user);
    }

    @Override
    public Flux<User> findAll() {
        return Flux.fromIterable(users.values());
    }

    @Override
    public Mono<User> findById(Long id) {
        return Mono.justOrEmpty(users.getOrDefault(id, null));
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        User user = users.getOrDefault(id, null);

        if (user != null) {
            users.remove(id, user);
        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteByName(String name) {
        return null;
    }
}
