package org.example.elsgraphql.service;

import org.example.elsgraphql.model.ChatMessage;
import org.reactivestreams.Publisher;

import java.util.List;

public interface ChatService {
    void sendMessage(String courseId, String userId, String content);

    List<ChatMessage> getMessages(String courseId);

    Publisher<ChatMessage> messageReceived(String courseId);
}
