package org.example.elsgateway.filter;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @LoadBalanced
    private final WebClient webClient;

    public AuthenticationFilter(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        super(Config.class);
        this.webClient = WebClient.builder()
                .filter(lbFunction)
                .baseUrl("http://els-user")
                .build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                return validateToken(token)
                        .flatMap(userId -> proceedWithUserId(userId, exchange, chain))
                        .switchIfEmpty(chain.filter(exchange))
                        .onErrorResume(e -> handleAuthenticationError(exchange, e));
            }

            return chain.filter(exchange);
        };
    }

    private Mono<? extends Void> handleAuthenticationError(ServerWebExchange exchange, Throwable e) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Long> validateToken(String token) {
        return webClient.post()
                .uri("/auth/validate")
                .header("Content-type", "application/json")
                .bodyValue("{\"token\": \"%s\"}".formatted(token))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> Long.valueOf(response.get("id").toString()));
    }

    private Mono<Void> proceedWithUserId(Long userId, ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getRequest().mutate().header("X-USER-ID", String.valueOf(userId));
        return chain.filter(exchange);
    }

    public static class Config {

    }
}
