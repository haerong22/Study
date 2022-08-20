package com.example.chatting.handler;

import com.example.chatting.domain.dto.ChatMessage;
import com.example.chatting.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageHandler implements ChannelInterceptor {

    private final RedisPublisher redisPublisher;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        StompPrincipal simpUser = (StompPrincipal) accessor.getHeader("simpUser");
        System.out.println("simpUser = " + simpUser);

        String destination = accessor.getDestination();
        StompCommand command = accessor.getCommand();
        String sessionId = accessor.getSessionId();
        SimpMessageType messageType = accessor.getMessageType();


        if (accessor.getCommand() == StompCommand.SUBSCRIBE) {
            System.out.println("destination = " + destination);
            System.out.println("command = " + command);
            System.out.println("sessionId = " + sessionId);
            System.out.println("messageType = " + messageType);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSessionId("session");
            chatMessage.setMessage("interceptor");
            redisPublisher.publish("message", chatMessage);
        }

        return message;
    }
}
