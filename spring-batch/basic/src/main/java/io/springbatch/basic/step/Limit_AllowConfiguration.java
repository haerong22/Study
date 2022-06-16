package io.springbatch.basic.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
public class Limit_AllowConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    /*
        startLimit()

        - Step 의 실행 횟수를 조정
        - Step 마다 설정 가능
        - 설정 값을 초과해서 다시 실행하려고 하면 StartLimitExceededException 발생
        - 디폴트는 Integer.MAX_VALUE

        allowStartIfComplete()

        - 재시작 가능한 job 에서 step 의 이전 성공 여부와 상관없이 항상 step 을 실행하기 위한 설정
        - 실행 마다 유효성을 검증하는 Step 이나 사전 작업이 꼭 필요한 Step 등에 사용
        - 기본적으로 COMPLETED 상태를 가진 Step 은 Job 재시작시 실행하지 않고 스킵
        - true 로 설정 된 step 은 항상 실행

    */
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() { // 익명 클래스
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step1 was executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> { // 람다
                    System.out.println("step2 was executed");

                    throw new RuntimeException("step2 was failed");
//                    return RepeatStatus.FINISHED;
                })
                .startLimit(3)
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(new CustomTasklet()) // 구현 클래스
                .build();
    }
}
