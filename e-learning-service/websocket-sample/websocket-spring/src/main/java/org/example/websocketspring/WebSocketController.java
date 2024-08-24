package org.example.websocketspring;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/send")
    @SendTo("/topic/messages") // 해당 토픽으로 broadcast
    public String processMessage(String message) {
        return message;
    }
}
