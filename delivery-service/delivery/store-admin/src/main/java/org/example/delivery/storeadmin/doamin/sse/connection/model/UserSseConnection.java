package org.example.delivery.storeadmin.doamin.sse.connection.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.delivery.storeadmin.doamin.sse.connection.interfaces.ConnectionPool;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String uniqueKey;
    private final SseEmitter sseEmitter;

    private final ConnectionPool<String, UserSseConnection> connectionPool;
    private final ObjectMapper objectMapper;

    private UserSseConnection(
            String uniqueKey,
            ConnectionPool<String, UserSseConnection> connectionPool,
            ObjectMapper objectMapper
    ) {
        this.uniqueKey = uniqueKey;
        this.sseEmitter = new SseEmitter(1000L * 60);
        this.connectionPool = connectionPool;
        this.objectMapper = objectMapper;

        this.sseEmitter.onCompletion(() -> {
            connectionPool.onCompletionCallback(this);
        });

        this.sseEmitter.onTimeout(() -> {
            this.sseEmitter.complete();
        });

        this.sendMessage("onopen", "connect");
    }

    public static UserSseConnection connect(
            String uniqueKey,
            ConnectionPool<String, UserSseConnection> connectionPool,
            ObjectMapper objectMapper
    ) {
        UserSseConnection connection = new UserSseConnection(uniqueKey, connectionPool, objectMapper);
        connectionPool.addSession(uniqueKey, connection);
        return connection;
    }

    public void sendMessage(String eventName, Object data) {
        try {
            String json = this.objectMapper.writeValueAsString(data);
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .name(eventName)
                    .data(json);

            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(Object data) {
        try {
            String json = this.objectMapper.writeValueAsString(data);
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .data(json);

            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }
}
