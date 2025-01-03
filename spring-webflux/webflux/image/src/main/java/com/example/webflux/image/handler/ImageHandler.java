package com.example.webflux.image.handler;

import com.example.webflux.image.handler.dto.CreateRequest;
import com.example.webflux.image.handler.dto.ImageResponse;
import com.example.webflux.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ImageHandler {

    private final ImageService imageService;

    public Mono<ServerResponse> getImageById(ServerRequest serverRequest) {
        String imageId = serverRequest.pathVariable("imageId");

        return imageService.getImageById(imageId)
                .map(image ->
                        new ImageResponse(image.getId(), image.getName(), image.getUrl())
                )
                .flatMap(imageResp ->
                        ServerResponse.ok().bodyValue(imageResp)
                )
                .onErrorMap(e -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Mono<ServerResponse> addImage(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateRequest.class)
                .flatMap(createRequest ->
                        imageService.createImage(
                                createRequest.getId(),
                                createRequest.getName(),
                                createRequest.getUrl()
                        )
                ).flatMap(imageResp ->
                        ServerResponse.ok().bodyValue(imageResp)
                );
    }
}
