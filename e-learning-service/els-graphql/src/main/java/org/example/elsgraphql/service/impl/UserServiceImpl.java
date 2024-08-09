package org.example.elsgraphql.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.elsgraphql.model.User;
import org.example.elsgraphql.service.UserService;
import org.example.elsgraphql.service.dto.UserDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_SERVICE_URL = "http://els-user/users";

    private final RestTemplate restTemplate;

    @Override
    public User createUser(String name, String email, String password) {
        UserDTO userDTO = new UserDTO(name, email, password);
        return restTemplate.postForObject(USER_SERVICE_URL, userDTO, User.class);
    }

    @Cacheable(value = "user", key = "#userId")
    @Override
    public Optional<User> findById(Long userId) {
        String url = UriComponentsBuilder.fromHttpUrl(USER_SERVICE_URL)
                .path("/{userId}")
                .buildAndExpand(userId)
                .toUriString();
        User user = restTemplate.getForObject(url, User.class);

        return Optional.ofNullable(user);
    }

    @Override
    public User updateUser(Long userId, String name, String email) {
        UserDTO userDTO = new UserDTO(name, email, null);

        String url = UriComponentsBuilder.fromHttpUrl(USER_SERVICE_URL)
                .path("/{userId}")
                .buildAndExpand(userId)
                .toUriString();
        restTemplate.put(url, userDTO);

        return new User(userId, name, email);
    }
}