package com.example.webflux.repository.r2dbc;

import com.example.webflux.repository.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserR2dbcRepository extends ReactiveCrudRepository<User, Long> {
}
