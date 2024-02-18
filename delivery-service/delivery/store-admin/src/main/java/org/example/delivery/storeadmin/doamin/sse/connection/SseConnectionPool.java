package org.example.delivery.storeadmin.doamin.sse.connection;

import lombok.extern.slf4j.Slf4j;
import org.example.delivery.storeadmin.doamin.sse.connection.interfaces.ConnectionPool;
import org.example.delivery.storeadmin.doamin.sse.connection.model.UserSseConnection;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SseConnectionPool implements ConnectionPool<String, UserSseConnection> {

    private static final Map<String, UserSseConnection> CONNECTION_POOL = new ConcurrentHashMap<>();


    @Override
    public void addSession(String uniqueKey, UserSseConnection session) {
        CONNECTION_POOL.put(uniqueKey, session);
    }

    @Override
    public UserSseConnection getSession(String uniqueKey) {
        return CONNECTION_POOL.get(uniqueKey);
    }

    @Override
    public void onCompletionCallback(UserSseConnection session) {
        log.info("call back connection pool completion : {}", session);
        CONNECTION_POOL.remove(session.getUniqueKey());
    }
}
