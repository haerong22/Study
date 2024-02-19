package io.springbatch.basic.domain.job.jobexecution;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

//@Configuration
@RequiredArgsConstructor
public class JobExecutionConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 was executed");

                    /*
                        jobExecution 은 jobInstance 에 대한 한 번의 시도를 의미
                        BATCH_JOB_EXECUTION 테이블에 실행 정보가 저장
                        만약 실행 도중 에러가 발생하면 STATUS 가 FAILED 로 저장되고
                        같은 jobInstance 라도 상태가 COMPLETED 가 없다면 계속 생성
                     */
//                    throw new RuntimeException("step2 has failed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
