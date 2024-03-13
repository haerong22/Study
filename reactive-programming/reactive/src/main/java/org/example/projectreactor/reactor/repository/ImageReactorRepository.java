package org.example.projectreactor.reactor.repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.projectreactor.common.repository.ImageEntity;
import org.example.projectreactor.common.repository.UserEntity;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class ImageReactorRepository {
    private final Map<String, ImageEntity> imageMap;

    public ImageReactorRepository() {
        imageMap = Map.of(
                "image#1000", new ImageEntity("image#1000", "profileImage", "https://dailyone.com/images/1000")
        );
    }

    @SneakyThrows
    public Mono<ImageEntity> findById(String id) {
        return Mono.create(sink -> {
            log.info("ImageRepository.findById: {}", id);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            ImageEntity imageEntity = imageMap.get(id);
            if (imageEntity == null) {
                sink.error(new RuntimeException("image not found"));
            } else {
                sink.success(imageEntity);
            }
        });
    }

    @SneakyThrows
    public Mono<ImageEntity> findWithContext() {
        return Mono.deferContextual(context -> {
            Optional<UserEntity> user = context.getOrEmpty("user");

            if (user.isEmpty()) throw new RuntimeException("user not found");

            return Mono.just(user.get().getProfileImageId());
        }).flatMap(this::findById);
    }
}