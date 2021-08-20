package com.example.web;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RRemoteService;
import org.redisson.api.RedissonClient;
import org.redisson.api.RemoteInvocationOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RedissonClient redissonClient;

    @GetMapping("/hello")
    public String callRemoteService() {
        RemoteInvocationOptions remoteOptions = RemoteInvocationOptions.defaults().noAck().noResult();
        RRemoteService remoteService = redissonClient.getRemoteService();
        RemoteServiceInterface service = remoteService.get(RemoteServiceInterface.class, remoteOptions);
        service.sayHello("kim");
        return "success";
    }

    @GetMapping("/hi/{name}")
    public String receiveRemoteService(@PathVariable String name) {
        System.out.println("hello " + name);
        return "hi " + name;
    }
}
