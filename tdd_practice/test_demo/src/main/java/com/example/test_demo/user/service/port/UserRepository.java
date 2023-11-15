package com.example.test_demo.user.service.port;

import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserStatus;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmailAndStatus(String email, UserStatus active);

    Optional<User> findByIdAndStatus(long id, UserStatus active);

    User save(User user);

    Optional<User> findById(long id);
}
