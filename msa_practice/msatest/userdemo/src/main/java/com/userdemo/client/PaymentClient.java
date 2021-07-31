package com.userdemo.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "payment", url = "${external-api.payment.url}")
public interface PaymentClient {
}
