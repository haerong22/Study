package com.example.test_demo.user.service.port;

import com.example.test_demo.user.domain.UserStatus;
import com.example.test_demo.user.infrastructure.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus active);

    Optional<UserEntity> findByIdAndStatus(long id, UserStatus active);

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(long id);
}
