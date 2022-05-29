package com.example.springbatch.application.scheduler;

import com.example.springbatch.application.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobScheduler {

    private final JobService jobService;

//    @Scheduled(fixedDelay = 5000)
    public void runCreateArticleJob() throws Exception{
        jobService.runCreateArticleJob();
    }
}
