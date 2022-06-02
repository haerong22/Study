package io.springbatch.basic.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StepConfiguration {

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

    /*
        Step 의 기본 구현체
        - TaskletStep : 기본 구현체
        - PartitionStep : 멀티 스레드 방식으로 Step 을 여러 개로 분리 실행
        - JobStep : Step 내에서 Job 실행
        - FlowStep : Step 내에서 Flow 실행

        1. TaskletStep 생성 (stepName, tasklet 저장)
        2. SimpleJob 생성 (tasklet 가지고 있음)
        3. SimpleJobLauncher 가 SimpleJob 실행 (StepHandler 가 실행)
        4. 내부 TaskletStep 실행
     */
    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new CustomTasklet())
                .build();
    }
}
