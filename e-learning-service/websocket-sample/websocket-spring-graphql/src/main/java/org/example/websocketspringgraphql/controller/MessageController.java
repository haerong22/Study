package org.example.websocketspringgraphql.controller;

import lombok.RequiredArgsConstructor;
import org.example.websocketspringgraphql.entity.Message;
import org.example.websocketspringgraphql.entity.MessageEvent;
import org.example.websocketspringgraphql.repository.MessageRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepository;
    private final ApplicationEventPublisher publisher;

    @QueryMapping
    public List<Message> messages() {
        return messageRepository.findAll();
    }

    @MutationMapping
    public Message postMessage(@Argument String content, @Argument String sender) {
        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);

        messageRepository.save(message);
        publisher.publishEvent(new MessageEvent(this, message));
        return message;
    }
}
