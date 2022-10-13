package org.example.service;

import org.example.annotation.Inject;
import org.example.annotation.Service;
import org.example.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
