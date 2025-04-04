package org.example.moviedgs.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Configuration
class DataLoaderExecutor {

    @Bean(name = ["DataLoaderThreadPool"])
    fun dataLoaderExecutor(): Executor {
        return Executors.newCachedThreadPool()
    }
}