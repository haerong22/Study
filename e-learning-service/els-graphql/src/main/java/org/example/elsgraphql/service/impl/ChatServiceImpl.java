package org.example.elsgraphql.service.impl;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.chat.domain.service.Chat;
import org.example.chat.domain.service.ChatServiceGrpc;
import org.example.elsgraphql.model.ChatMessage;
import org.example.elsgraphql.service.ChatService;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.UUID;

@Service
public class ChatServiceImpl implements ChatService {

    @GrpcClient("els-chat")
    private ChatServiceGrpc.ChatServiceBlockingStub blockingStub;

    @GrpcClient("els-chat")
    private ChatServiceGrpc.ChatServiceStub asyncStub;

    @Override
    public void sendMessage(String courseId, String userId, String content) {
        String messageId = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();

        Chat.ChatMessage grpcMessage = Chat.ChatMessage.newBuilder()
                .setCourseId(courseId)
                .setUserId(userId)
                .setMessageId(messageId)
                .setContent(content)
                .setTimestamp(timestamp)
                .build();

        Chat.SendMessageRequest request = Chat.SendMessageRequest.newBuilder()
                .setMessage(grpcMessage)
                .build();

        Chat.SendMessageResponse response = blockingStub.sendMessage(request);
        if (!response.getSuccess()) {
            throw new RuntimeException("Failed to send message: " + response.getError());
        }
    }

    @Override
    public List<ChatMessage> getMessages(String courseId) {
        Chat.GetMessagesRequest request = Chat.GetMessagesRequest.newBuilder()
                .setCourseId(courseId)
                .build();

        return blockingStub.getMessages(request).getMessagesList().stream()
                .map(ChatMessage::fromProto)
                .toList();
    }

    @Override
    public Publisher<ChatMessage> messageReceived(String courseId) {
        return Flux.create(sink -> {
            Chat.SubscribeMessagesRequest request = Chat.SubscribeMessagesRequest.newBuilder()
                    .setCourseId(courseId)
                    .build();

            asyncStub.subscribeMessages(request, new StreamObserver<>() {

                @Override
                public void onNext(Chat.ChatMessage chatMessage) {
                    ChatMessage message = ChatMessage.fromProto(chatMessage);
                    sink.next(message);
                }

                @Override
                public void onError(Throwable throwable) {
                    sink.error(throwable);
                }

                @Override
                public void onCompleted() {
                    sink.complete();
                }
            });

        }, FluxSink.OverflowStrategy.BUFFER);
    }
}
