package io.springbatch.basic.flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlowJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*
        FlowJob

        - SimpleJob : 순차적 흐름 / FlowJob : 조건적 흐름
        - Step 을 순차적으로만 구성하는 것이 아닌 특정한 상태에 따라 흐름을 전환하도록 구성
            - Step 이 실패 하더라도 Job 은 실패로 끝나지 않도록 해야 하는 경우
            - Step 이 성공 했을 때 다음에 실행해야 할 Step 을 구분해서 실행해야 하는 경우
            - 특정 Step 은 전혀 실행되지 않게 구성해야 하는 경우
        - Flow 와 Job 의 흐름을 구성하는데만 관여하고 실제 비즈니스 로직은 Step 에서 이루어진다.
        - 내부적으로 SimpleFlow 객체를 포함하고 있으며 Job 실행 시 호출
        - JobBuilderFactory -> JobBuilder -> JobFlowBuilder -> FlowBuilder -> FlowJob
     */
    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .on("COMPLETED")                // Step 의 실행 결과로 돌려받는 종료상태(ExitStatus) 를 캐치하여 매칭, TransitionBuilder 반환
                .to(step3())                           // 다음으로 이동할 Step 지정
                .from(step1())                         // 이전 단계에서 정의한 Step 의 Flow 를 추가적으로 정의
                .on("FAILED")
                .to(step2())                           // Step1 이 실패할 경우 Step2 실행
                .end()                                 // build() 앞에 위치하면 FlowBuilder 를 종료하고 SimpleFlow 객체 생성
                .build();                              // FlowJob 생성하고 flow 필드에 SimpleFlow 저장
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 was executed");
                    throw new RuntimeException("step1 was failed");
//                    return RepeatStatus.FINISHED;
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
