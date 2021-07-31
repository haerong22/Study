package com.paymentdemo.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "user", url = "${external-api.user.url}")
public class UserDemoClient {
}
