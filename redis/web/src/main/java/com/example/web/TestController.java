package com.example.web;

import com.example.remoteinterface.service.RemoteServiceInterface;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RRemoteService;
import org.redisson.api.RedissonClient;
import org.redisson.api.RemoteInvocationOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RedissonClient redissonClient;

    @GetMapping("/hello")
    public String callRemoteService() {
        RemoteInvocationOptions remoteOptions = RemoteInvocationOptions.defaults().expectAckWithin(5000);
        RRemoteService remoteService = redissonClient.getRemoteService();
        RemoteServiceInterface service = remoteService.get(RemoteServiceInterface.class, remoteOptions);

        return service.sayHello("kim");
    }
}
