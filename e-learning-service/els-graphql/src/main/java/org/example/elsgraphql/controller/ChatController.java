package org.example.elsgraphql.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.elsgraphql.model.ChatMessage;
import org.example.elsgraphql.service.ChatService;
import org.reactivestreams.Publisher;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MutationMapping
    public boolean sendMessage(
            @Argument String courseId,
            @Argument String userId,
            @Argument String content
    ) {
        chatService.sendMessage(courseId, userId, content);
        return true;
    }

    @SubscriptionMapping
    public Publisher<ChatMessage> messageReceived(
            @Argument String courseId
    ) {
        return chatService.messageReceived(courseId);
    }

    @QueryMapping
    public List<ChatMessage> getMessages(
            @Argument String courseId
    ) {
        return chatService.getMessages(courseId);
    }
}
