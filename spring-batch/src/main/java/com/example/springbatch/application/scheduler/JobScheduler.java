package com.example.springbatch.application.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class JobScheduler {

    private final Job createArticleJob;
    private final JobLauncher jobLauncher;

    @Scheduled(fixedDelay = 5000)
    public void runCreateArticleJob() throws Exception{
        jobLauncher.run(createArticleJob, new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters());
    }
}
