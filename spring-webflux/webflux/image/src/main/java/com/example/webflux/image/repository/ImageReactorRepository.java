package com.example.webflux.image.repository;

import com.example.webflux.image.entity.ImageEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class ImageReactorRepository {
    private final ReactiveHashOperations<String, String, String> hashOperations;

    public ImageReactorRepository(
            ReactiveStringRedisTemplate redisTemplate
    ) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @SneakyThrows
    public Mono<ImageEntity> findById(String id) {
        return Mono.create(sink -> {
            log.info("ImageRepository.findById: {}", id);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                sink.error(new RuntimeException(e));
                return;
            }

            hashOperations.multiGet(id, List.of("id", "name", "url"))
                    .subscribe(strings -> {
                        if (strings.stream().allMatch(Objects::isNull)) {
                            sink.error(new RuntimeException("image not found"));
                            return;
                        }
                        var image = new ImageEntity(
                                strings.get(0),
                                strings.get(1),
                                strings.get(2)
                        );

                        sink.success(image);
                    });
        });
    }

    public Mono<ImageEntity> create(String id, String name, String url) {
        var map = Map.of("id", id, "name", name, "url", url);
        return hashOperations.putAll(id, map)
                .then(findById(id));
    }
}