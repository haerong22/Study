package com.paymentdemo.service;

import com.paymentdemo.model.dto.WebBookChapterPaymentDto;
import com.paymentdemo.model.entity.WebBookChapterPayment;
import com.paymentdemo.model.entity.repository.WebBookChapterPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final WebBookChapterPaymentRepository webBookChapterPaymentRepository;

    public Long webBookChapterPayment(WebBookChapterPaymentDto webBookChapterPaymentDto) {
        return webBookChapterPaymentRepository.save(
                WebBookChapterPayment.builder()
                        .webBookChapterId(webBookChapterPaymentDto.getWebBookChapterId())
                        .readerId(webBookChapterPaymentDto.getReaderId())
                        .amount(webBookChapterPaymentDto.getAmount())
                        .createdAt(LocalDateTime.now())
                        .build()
        ).getWebBookChapterPaymentId();
    }
}