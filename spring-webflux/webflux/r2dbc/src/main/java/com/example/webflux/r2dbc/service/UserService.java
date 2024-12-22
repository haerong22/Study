package com.example.webflux.r2dbc.service;

import com.example.webflux.r2dbc.common.EmptyImage;
import com.example.webflux.r2dbc.common.Image;
import com.example.webflux.r2dbc.common.User;
import com.example.webflux.r2dbc.repository.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

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
                            .map(image -> new User(
                                    userEntity.getId().toString(),
                                    userEntity.getName(),
                                    userEntity.getAge(),
                                    image
                            ));
                });
    }
}