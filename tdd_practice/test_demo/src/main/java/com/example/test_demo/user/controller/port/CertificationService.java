package com.example.test_demo.user.controller.port;

public interface CertificationService {
    void send(String email, long id, String certificationCode);
}
