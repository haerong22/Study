package org.example.elschat.domain;

import io.grpc.stub.StreamObserver;
import org.example.chat.domain.service.Chat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @InjectMocks
    private ChatService chatService;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @Mock
    private RedisMessageListenerContainer redisContainer;

    @Test
    public void testSendMessage() {
        // given
        given(redisTemplate.opsForHash()).willReturn(hashOperations);

        Chat.ChatMessage message = Chat.ChatMessage.newBuilder()
                .setCourseId("course1")
                .setMessageId("msg1")
                .setUserId("user1")
                .setContent("Hello")
                .setTimestamp(System.currentTimeMillis())
                .build();

        Chat.SendMessageRequest request = Chat.SendMessageRequest.newBuilder()
                .setMessage(message)
                .build();

        StreamObserver<Chat.SendMessageResponse> responseObserver = mock(StreamObserver.class);

        // when
        chatService.sendMessage(request, responseObserver);

        // then
        ArgumentCaptor<Chat.SendMessageResponse> responseCaptor = ArgumentCaptor.forClass(Chat.SendMessageResponse.class);
        verify(responseObserver).onNext(responseCaptor.capture());
        verify(responseObserver).onCompleted();

        Chat.SendMessageResponse response = responseCaptor.getValue();
        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getError()).isEqualTo("");
    }

    @Test
    public void testSubscribeMessages() {
        // given
        Chat.SubscribeMessagesRequest request = Chat.SubscribeMessagesRequest.newBuilder()
                .setCourseId("course1")
                .build();

        StreamObserver<Chat.ChatMessage> responseObserver = mock(StreamObserver.class);

        // when
        chatService.subscribeMessages(request, responseObserver);

        // then
        String channelName = "course:course1";
        ArgumentCaptor<MessageListenerAdapter> listenerCaptor = ArgumentCaptor.forClass(MessageListenerAdapter.class);
        verify(redisContainer).addMessageListener(listenerCaptor.capture(), eq(new ChannelTopic(channelName)));

        MessageListenerAdapter listener = listenerCaptor.getValue();
        assertThat(listener).isNotNull();

        String testMessage = "user1::Hello";
        Message redisMessage = new Message() {
            @Override
            public byte[] getBody() {
                return testMessage.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public byte[] getChannel() {
                return new byte[0];
            }
        };

        listener.onMessage(redisMessage, null);


        ArgumentCaptor<Chat.ChatMessage> messageCaptor = ArgumentCaptor.forClass(Chat.ChatMessage.class);
        verify(responseObserver).onNext(messageCaptor.capture());

        Chat.ChatMessage chatMessage = messageCaptor.getValue();
        assertThat(chatMessage.getUserId()).isEqualTo("user1");
        assertThat(chatMessage.getContent()).isEqualTo("Hello");
        assertThat(chatMessage.getCourseId()).isEqualTo("course1");
    }
}