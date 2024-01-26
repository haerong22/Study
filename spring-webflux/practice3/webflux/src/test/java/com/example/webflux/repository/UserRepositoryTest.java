package com.example.webflux.repository;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Test
    void save() {
        var user = User.builder().name("bobby").email("bobby@email.com").build();
        StepVerifier.create(userRepository.save(user))
                .assertNext(u -> {
                    assertEquals(1L, u.getId());
                    assertEquals("bobby", u.getName());
                })
                .verifyComplete();
    }

    @Test
    void findAll() {
        userRepository.save(User.builder().name("bobby").email("bobby@email.com").build());
        userRepository.save(User.builder().name("bobby2").email("bobby2@email.com").build());
        userRepository.save(User.builder().name("bobby3").email("bobby3@email.com").build());

        StepVerifier.create(userRepository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        userRepository.save(User.builder().name("bobby").email("bobby@email.com").build());
        userRepository.save(User.builder().name("bobby2").email("bobby2@email.com").build());

        StepVerifier.create(userRepository.findById(2L))
                .assertNext(u -> {
                    assertEquals(2L, u.getId());
                    assertEquals("bobby2", u.getName());
                })
                .verifyComplete();
    }

    @Test
    void deleteById() {
        userRepository.save(User.builder().name("bobby").email("bobby@email.com").build());
        userRepository.save(User.builder().name("bobby2").email("bobby2@email.com").build());

        StepVerifier.create(userRepository.deleteById(2L))
                .expectNextCount(1)
                .verifyComplete();
    }
}