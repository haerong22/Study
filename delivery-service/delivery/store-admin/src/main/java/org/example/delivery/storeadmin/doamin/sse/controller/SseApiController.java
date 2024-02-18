package org.example.delivery.storeadmin.doamin.sse.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.delivery.storeadmin.doamin.authorization.model.UserSession;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sse")
public class SseApiController {

    private static final Map<String, SseEmitter> userConnections = new ConcurrentHashMap<>();

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter connect(
            @Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession
    ) {
        log.info("login user: {}", userSession);

        SseEmitter emitter = new SseEmitter(1000L * 60);
        userConnections.put(userSession.getUserId().toString(), emitter);

        emitter.onTimeout(() -> {
            log.info("on timeout");
            emitter.complete();
        });

        emitter.onCompletion(() -> {
            log.info("on completion");
            userConnections.remove(userSession.getUserId().toString());
        });

        SseEmitter.SseEventBuilder event = SseEmitter.event()
                .name("onopen")
                .data("connect");

        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    @GetMapping("/push-event")
    public void pushEvent(
            @Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession
    ) {
        SseEmitter emitter = userConnections.get(userSession.getUserId().toString());

        SseEmitter.SseEventBuilder event = SseEmitter.event().data("hello");

        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
