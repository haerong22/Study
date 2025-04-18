package org.example.elschat.domain;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.chat.domain.service.Chat;
import org.example.chat.domain.service.ChatServiceGrpc;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {

    private final RedisTemplate<String, String> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisMessageListenerContainer redisContainer;

    @Override
    public void getMessages(Chat.GetMessagesRequest request, StreamObserver<Chat.GetMessagesResponse> responseObserver) {
        String courseId = request.getCourseId();
        String pattern = "course:%s:message:*".formatted(courseId);

        try {
            Set<String> keys = redisTemplate.keys(pattern);
            List<Chat.ChatMessage> messages = new ArrayList<>();
            if (keys != null) {
                for (String key : keys) {
                    Map<Object, Object> messageData = redisTemplate.opsForHash().entries(key);
                    Chat.ChatMessage message = Chat.ChatMessage.newBuilder()
                            .setCourseId(courseId)
                            .setUserId((String) messageData.get("user_id"))
                            .setMessageId(key.split(":")[3])
                            .setContent((String) messageData.get("content"))
                            .setTimestamp(Long.parseLong((String) messageData.get("timestamp")))
                            .build();
                    messages.add(message);
                }
            }
            Chat.GetMessagesResponse response = Chat.GetMessagesResponse.newBuilder()
                    .addAllMessages(messages)
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("getMessages error : ", e);
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void sendMessage(Chat.SendMessageRequest request, StreamObserver<Chat.SendMessageResponse> responseObserver) {
        Chat.ChatMessage message = request.getMessage();
        String redisKey = "course:%s:message:%s".formatted(message.getCourseId(), message.getMessageId());
        String channel = "course:%s".formatted(message.getCourseId());

        try {
            redisTemplate.opsForHash().put(redisKey, "user_id", message.getUserId());
            redisTemplate.opsForHash().put(redisKey, "content", message.getContent());
            redisTemplate.opsForHash().put(redisKey, "timestamp", String.valueOf(message.getTimestamp()));

            // Broadcast message
            stringRedisTemplate.convertAndSend(channel, message.getUserId() + "::" + message.getContent() + "::" + message.getMessageId() + "::" + message.getTimestamp());

            Chat.SendMessageResponse response = Chat.SendMessageResponse.newBuilder()
                    .setSuccess(true)
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            Chat.SendMessageResponse response = Chat.SendMessageResponse.newBuilder()
                    .setSuccess(false)
                    .setError("Failed to send message: " + e.getMessage())
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void subscribeMessages(Chat.SubscribeMessagesRequest request, StreamObserver<Chat.ChatMessage> responseObserver) {
        MessageListenerAdapter messageListener = null;

        try {
            String channel = "course:%s".formatted(request.getCourseId());
            messageListener = new MessageListenerAdapter((MessageListener) (message, pattern) -> {
                // Redis로부터 받은 메시지의 바디를 문자열로 변환
                String messageContent = new String(message.getBody(), StandardCharsets.UTF_8);
                String[] splitMessage = messageContent.split("::");
                Chat.ChatMessage chatMessage = Chat.ChatMessage.newBuilder()
                        .setTimestamp(Long.parseLong(splitMessage[3]))
                        .setMessageId(splitMessage[2])
                        .setContent(splitMessage[1])
                        .setUserId(splitMessage[0])
                        .setCourseId(request.getCourseId())
                        .build();
                responseObserver.onNext(chatMessage);
            });

            redisContainer.addMessageListener(messageListener, new ChannelTopic(channel));
        } catch (Exception e) {
            log.error("subscribeMessages error : ", e);
            if (messageListener != null) {
                redisContainer.removeMessageListener(messageListener);
            }
        }
    }
}
