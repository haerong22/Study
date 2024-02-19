package org.example.delivery.storeadmin.doamin.sse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.delivery.storeadmin.doamin.authorization.model.UserSession;
import org.example.delivery.storeadmin.doamin.sse.connection.SseConnectionPool;
import org.example.delivery.storeadmin.doamin.sse.connection.model.UserSseConnection;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sse")
public class SseApiController {

    private final SseConnectionPool sseConnectionPool;
    private final ObjectMapper objectMapper;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter connect(
            @Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession
    ) {
        log.info("login user: {}", userSession);

        UserSseConnection connection = UserSseConnection.connect(
                userSession.getStoreId().toString(),
                sseConnectionPool,
                objectMapper
        );

        return connection.getSseEmitter();
    }

    @GetMapping("/push-event")
    public void pushEvent(
            @Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession
    ) {
        UserSseConnection connection = sseConnectionPool.getSession(userSession.getStoreId().toString());

        Optional.ofNullable(connection)
                .ifPresent(it -> it.sendMessage("hello world!"));
    }
}
