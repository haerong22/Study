package org.example.elsgraphql.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Configuration
public class UserInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        log.info("GraphQL Request: {}", request.getDocument());
        log.info("Headers: {}", request.getHeaders());

        String userId = request.getHeaders().getFirst("X-USER-ID");
        String userRole = request.getHeaders().getFirst("X-USER-ROLE");

        request.configureExecutionInput((executionInput, builder) -> {
            executionInput.getGraphQLContext().put("X-USER-ID", Objects.requireNonNullElse(userId, "-1"));
            executionInput.getGraphQLContext().put("X-USER-ROLE", Objects.requireNonNullElse(userRole, "user"));

            return executionInput;
        });

        return chain.next(request);
    }
}
