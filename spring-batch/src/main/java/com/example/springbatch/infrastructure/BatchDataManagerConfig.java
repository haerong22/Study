package com.example.springbatch.infrastructure;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchDataManagerConfig extends DefaultBatchConfigurer {

    private final PlatformTransactionManager batchTransactionManager;

    public BatchDataManagerConfig(
            @Qualifier("batchTransactionManager") PlatformTransactionManager batchTransactionManager) {
        this.batchTransactionManager = batchTransactionManager;
    }

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return this.batchTransactionManager;
    }
}
