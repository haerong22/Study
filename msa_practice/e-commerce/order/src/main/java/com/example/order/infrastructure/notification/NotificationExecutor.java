package com.example.order.infrastructure.notification;

import com.example.order.domain.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationExecutor implements NotificationService {

    @Override
    public void sendEmail(String email, String title, String content) {
        log.info("sendEmail");
    }

    @Override
    public void sendKakao(String phoneNo, String content) {
        log.info("sendKakao");
    }

    @Override
    public void sendSms(String phoneNo, String content) {
        log.info("sendSms");
    }
}
