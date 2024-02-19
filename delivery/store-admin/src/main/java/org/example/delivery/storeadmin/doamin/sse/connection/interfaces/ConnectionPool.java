package org.example.delivery.storeadmin.doamin.sse.connection.interfaces;

public interface ConnectionPool<T, R> {

    void addSession(T uniqueKey, R session);

    R getSession(T uniqueKey);

    void onCompletionCallback(R session);
}
