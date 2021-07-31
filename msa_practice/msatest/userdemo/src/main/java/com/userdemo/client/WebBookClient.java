package com.userdemo.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "webBook", url = "${external-api.webBook.url}")
public interface WebBookClient {

}
