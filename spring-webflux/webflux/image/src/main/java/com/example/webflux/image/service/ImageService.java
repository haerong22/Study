package com.example.webflux.image.service;

import com.example.webflux.image.entity.Image;
import com.example.webflux.image.repository.ImageReactorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageReactorRepository imageRepository;

    public Mono<Image> getImageById(String imageId) {
        return imageRepository.findById(imageId)
                .map(imageEntity ->
                        new Image(
                                imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()
                        )
                );
    }
}
