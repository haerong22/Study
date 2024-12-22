package com.example.webflux.r2dbc.service;

import com.example.webflux.r2dbc.common.EmptyImage;
import com.example.webflux.r2dbc.common.Image;
import com.example.webflux.r2dbc.common.User;
import com.example.webflux.r2dbc.common.repository.UserEntity;
import com.example.webflux.r2dbc.repository.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserR2dbcRepository userRepository;

    private final WebClient webClient = WebClient.create("http://localhost:8081");

    public Mono<User> findById(String userId) {
        return userRepository.findById(Long.parseLong(userId))
                .flatMap(userEntity -> {
                    String imageId = userEntity.getProfileImageId();

                    Map<String, String> uriVariableMap = Map.of("imageId", imageId);
                    return webClient.get()
                            .uri("/api/images/{imageId}", uriVariableMap)
                            .retrieve()
                            .bodyToMono(ImageResponse.class)
                            .map(imageResp -> new Image(
                                    imageResp.getId(),
                                    imageResp.getName(),
                                    imageResp.getUrl()
                            ))
                            .switchIfEmpty(Mono.just(new EmptyImage()))
                            .map(image -> {
                                Optional<Image> profileImage = Optional.empty();
                                if (!(image instanceof EmptyImage)) {
                                    profileImage = Optional.of(image);
                                }
                                return map(userEntity, profileImage);
                            });
                });
    }

    @Transactional
    public Mono<User> createUser(String name, Integer age, String password, String profileImageId) {
        var newUser = new UserEntity(
                name,
                age,
                profileImageId,
                password
        );
        return userRepository.save(newUser)
                .map(userEntity ->
                        map(userEntity, Optional.of(new EmptyImage()))
                );
    }

    private User map(UserEntity userEntity, Optional<Image> profileImage) {
        return new User(
                userEntity.getId().toString(),
                userEntity.getName(),
                userEntity.getAge(),
                profileImage,
                List.of(),
                0L
        );
    }
}