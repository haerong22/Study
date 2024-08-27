package org.example.elsgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chat.domain.service.Chat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String courseId;
    private String userId;
    private String messageId;
    private String content;
    private Long timestamp;

    public static ChatMessage fromProto(Chat.ChatMessage message) {
        return new ChatMessage(
                message.getCourseId(),
                message.getUserId(),
                message.getMessageId(),
                message.getContent(),
                message.getTimestamp()
        );
    }
}