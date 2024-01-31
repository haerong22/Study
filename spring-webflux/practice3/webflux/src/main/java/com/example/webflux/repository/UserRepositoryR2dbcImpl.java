package com.example.webflux.repository;

import com.example.webflux.repository.r2dbc.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepositoryR2dbcImpl implements UserRepository {

    private final UserR2dbcRepository userR2dbcRepository;

    @Override
    public Mono<User> save(User user) {
        return userR2dbcRepository.save(user);
    }

    @Override
    public Flux<User> findAll() {
        return userR2dbcRepository.findAll();
    }

    @Override
    public Mono<User> findById(Long id) {
        return userR2dbcRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return userR2dbcRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteByName(String name) {
        return userR2dbcRepository.deleteByName(name);
    }
}
