package com.example.webflux.image.config;

import com.example.webflux.image.handler.ImageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteConfig {

    @Bean
    RouterFunction router(
            ImageHandler imageHandler
    ) {
        return route()
                .path("/api", r1 -> r1
                        .path("/images", r2 -> r2
                                .GET("/{imageId:[0-9]+}", imageHandler::getImageById)
                                .POST(imageHandler::addImage)
                        )
                )
                .build();
    }
}
