package com.userdemo.client;

import com.userdemo.model.dto.WebBookChapterPaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payment", url = "${external-api.payment.url}")
public interface PaymentClient {

    @PostMapping
    Long webBookChapterPayment(@RequestBody WebBookChapterPaymentDto build);
}
