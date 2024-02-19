package io.springbatch.basic.flow;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class CustomJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().putString("name", "user1");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}
