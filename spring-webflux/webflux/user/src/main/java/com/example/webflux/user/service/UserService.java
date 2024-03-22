package com.example.webflux.user.service;

import com.example.webflux.user.entity.User;
import com.example.webflux.user.repository.UserReactorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserReactorRepository userRepository;

    public Mono<User> findById(String userId) {
        return userRepository.findById(userId)
                .map(userEntity ->
                        new User(
                                userEntity.getId(),
                                userEntity.getName(),
                                userEntity.getAge()
                        )
                );
    }
}