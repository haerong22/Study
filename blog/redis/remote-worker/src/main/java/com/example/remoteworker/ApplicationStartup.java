package com.example.remoteworker;

import com.example.remoteworker.service.RemoteService;
import com.example.remoteinterface.service.RemoteServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRemoteService;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final RedissonClient redissonClient;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("onApplicationReady time={}", event.getTimestamp());
        try {
            registerRemoteService();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void registerRemoteService() throws Exception {
        log.info("RedissonClient config={}", redissonClient.getConfig());
        RRemoteService remoteService = redissonClient.getRemoteService();

        RemoteService remote = new RemoteService();
        remoteService.register(RemoteServiceInterface.class, remote);
        log.info("Remote service is registered!!");
    }
}
