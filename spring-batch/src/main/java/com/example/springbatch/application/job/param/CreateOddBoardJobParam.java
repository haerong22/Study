package com.example.springbatch.application.job.param;

import lombok.Getter;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
@Getter
public class CreateOddBoardJobParam {

    private long minId;
    private long maxId;

    @Value("#{jobParameters[minId]}")
    private void setMinId(long minId) {
        this.minId = minId;
    }

    @Value("#{jobParameters[maxId]}")
    private void setMaxId(long maxId) {
        this.maxId = maxId;
    }
}
