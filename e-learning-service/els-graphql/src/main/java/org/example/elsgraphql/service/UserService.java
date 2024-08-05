package org.example.elsgraphql.service;

import org.example.elsgraphql.model.User;

import java.util.Optional;

public interface UserService {
    User createUser(String name, String email, String password);

    Optional<User> findById(Long id);

    User updateUser(Long id, String name, String email);
}
