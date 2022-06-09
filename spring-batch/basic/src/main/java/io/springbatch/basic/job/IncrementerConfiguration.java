package io.springbatch.basic.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
public class IncrementerConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*
        incrementer()

        JobParameters 에서 필요한 값을 증가시켜 다음에 사용될 JobParameters 객체 리턴
        기존의 JobParameter 변경 없이 Job 을 여러번 시작이 필요할 경우
        RunIdIncrementer 구현체 지원, 직접 구현 가능
     */
    @Bean
    public Job batchJob1() {
        return jobBuilderFactory.get("batchJob1")
                .start(step1())
                .next(step2())
                .next(step3())
                .incrementer(new CustomJobParametersIncrementer())
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
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step3 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
