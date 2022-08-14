package io.springbatch.basic.step;

import io.springbatch.basic.job.CustomJobParametersIncrementer;
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
public class TaskletConfiguration {

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
        tasklet()

        - Step 내에서 구성되고 실행되는 도메인 객체로써 주로 단일 태스크를 수행
        - Tasklet 에 의해 반복적으로 수행되며 반환값에 따라 계속 수행 혹은 종료
        - RepeatStatus
            - RepeatStatus.FINISHED : Tasklet 종료 RepeatStatus 를 null 로 반환해도 같다.
            - RepeatStatus.CONTINUABLE : Tasklet 반복
            - RepeatStatus.FINISHED 가 리턴되거나 실패 예외가 발생하기 전까지 반복적으로 호출
        - 익명클래스, 구현클래스를 만들어서 사용
        - 이 메소드를 실행하면 TaskletStepBuilder 가 반환되어 관련 API 설정 가능
        - Step 에 하나의 Tasklet 설정이 가능하며 두개 이상 설정 했을 경우 마지막에 설정한 객체가 실행

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
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> { // 람다
                    System.out.println("step2 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(new CustomTasklet()) // 구현 클래스
                .build();
    }
}
