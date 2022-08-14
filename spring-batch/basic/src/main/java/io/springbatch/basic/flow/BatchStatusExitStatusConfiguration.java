package io.springbatch.basic.flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
public class BatchStatusExitStatusConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*
        Transition

        - 배치 상태 유형
            - BatchStatus
                - JobExecution 과 StepExecution 의 속성으로 Job 과 Step 의 종료 후 최종 결과 상태
                - SimpleJob
                    - 마지막 Step 의 BatchStatus 값을 Job 의 최종 BatchStatus 값으로 반영
                    - Step 이 실패할 경우 해당 Step 이 마지막 Step 이 된다.
                - FlowJob
                    - Flow 내 Step 의 ExitStatus 값을 FlowExecutionStatus 값으로 저장
                    - 마지막 Flow 의 FlowExecutionStatus 값을 Job 의 최종 BatchStatus 값으로 반영
                - COMPLETED, STARTING, STARTED, STOPPING, STOPPED, FAILED, ABANDONED, UNKNOWN
                - ABANDONED 는 처리를 완료했지만 성공하지 못한 단계와 재시작시 건너 뛰어야하는 단계

            - ExitStatus
                - JobExecution 과 StepExecution 의 속성으로 Job 과 Step 의 실행 후 어떤 상태로 종료 되었는지 정의
                - 기본적으로 ExitStatus 는 BatchStatus 와 동일한 값으로 설정 된다.
                - SimpleJob
                    - 마지막 Step 의 ExitStatus 값을 Job 의 최종 ExitStatus 값으로 반영
                - FlowJob
                    - Flow 내 Step 의 ExitStatus 값을 FlowExecutionStatus 값으로 저장
                    - 마지막 Flow 의 FlowExecutionStatus 값을 Job 의 최종 ExitStatus 값으로 반영
                - UNKNOWN, EXECUTING, COMPLETED, NOOP, FAILED, STOPPED

            - FlowExecutionStatus
                - FlowExecution 의 속성으로 Flow 의 실행 후 최종 결과 상태가 무엇인지 정의
                - Flow 내 Step 이 실행되고 나서 ExitStatus 값을 FlowExecutionStatus 값으로 저장
                - FlowJob 의 배치 결과 상태에 관여
                - COMPLETED, STOPPED, FAILED, UNKNOWN
    */
//    @Bean
//    public Job batchJob() {
//        return jobBuilderFactory.get("batchJob")
//                .incrementer(new RunIdIncrementer())
//                .start(step1())
//                .next(step2())
//                .next(step3())
//                .build();
//    }

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .next(step2())
                .on("FAILED")
                .to(step3())
                .end()
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
                    contribution.setExitStatus(ExitStatus.FAILED);
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
