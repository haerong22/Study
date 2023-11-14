package com.example.test_demo.user.service.port;

public interface MailSender {

    void send(String email, String title, String content);
}
