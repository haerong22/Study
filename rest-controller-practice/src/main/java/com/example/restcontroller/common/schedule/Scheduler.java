package com.example.restcontroller.common.schedule;

import com.example.restcontroller.logs.service.LogService;
import com.example.restcontroller.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final LogService logService;
    private final UserService userService;

//    @Scheduled(cron = "0 0 4 * * *")
//    @Scheduled(fixedDelay = 1000 * 60)
    public void deleteLog() {
        log.info("################################");
        log.info("스케줄 실행!!!!");
        log.info("################################");

        logService.deleteLog();
    }
//    @Scheduled(cron = "0 0 4 * * *")
//    @Scheduled(fixedDelay = 1000 * 60)
    public void sendServiceNotice() {
        log.info("################################");
        log.info("스케줄 실행!!!!");
        log.info("################################");

        userService.sendServiceNotice();
    }
}
