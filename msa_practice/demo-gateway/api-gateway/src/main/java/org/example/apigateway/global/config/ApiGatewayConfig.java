package org.example.apigateway.global.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/bobby/**")
                        .filters(f -> f.rewritePath("/bobby/(?<segment>.*)", "/"))
                        .uri("https://google.com")
                )
                .build();
    }
}
