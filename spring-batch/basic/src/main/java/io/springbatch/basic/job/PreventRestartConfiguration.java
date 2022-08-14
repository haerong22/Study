package io.springbatch.basic.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
public class PreventRestartConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*
        preventRestart()

        Job 의 재시작 여부 설정 기본값은 true
        -> true 일 경우 Job 이 실패하면 재시작이 가능
        -> false 일 경우 Job 이 실패해도 재시작 불가능 (JobRestartException 발생)

        Job 의 실행이 처음이 아닌 경우는 Job 의 성공여부와 관계없이 preventRestart 설정 값으로 실행여부 판단
     */
    @Bean
    public Job batchJob1() {
        return jobBuilderFactory.get("batchJob1")
                .start(step1())
                .next(step2())
                .next(step3())
                .preventRestart() // 설정 할 경우 restartable 속성이 true -> false 로 변경
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
//                    throw new RuntimeException("step3 was failed");
                    System.out.println("step3 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
