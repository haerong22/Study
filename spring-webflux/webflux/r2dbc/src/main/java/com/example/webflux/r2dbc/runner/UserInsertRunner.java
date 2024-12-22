package com.example.webflux.r2dbc.runner;

import com.example.webflux.r2dbc.common.repository.UserEntity;
import com.example.webflux.r2dbc.repository.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

@Slf4j
@RequiredArgsConstructor
//@Component
public class UserInsertRunner implements CommandLineRunner {

    private final UserR2dbcRepository userR2dbcRepository;

    @Override
    public void run(String... args) throws Exception {
        var newUser = new UserEntity("bobby", 20, "1", "1234");
        var savedUser = userR2dbcRepository.save(newUser).block();
        log.info("savedUser: {}", savedUser);
    }
}
