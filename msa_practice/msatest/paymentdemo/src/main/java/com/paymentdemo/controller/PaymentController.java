package com.paymentdemo.controller;

import com.paymentdemo.model.dto.WebBookChapterPaymentDto;
import com.paymentdemo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    ResponseEntity<Long> webBookChapterPayment(WebBookChapterPaymentDto webBookChapterPaymentDto){
        return ResponseEntity.ok(paymentService.webBookChapterPayment(webBookChapterPaymentDto));
    }
}
