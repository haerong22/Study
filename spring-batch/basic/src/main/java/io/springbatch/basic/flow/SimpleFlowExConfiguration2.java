package io.springbatch.basic.flow;

import lombok.RequiredArgsConstructor;
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

@Configuration
@RequiredArgsConstructor
public class SimpleFlowExConfiguration2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*
        SimpleFlow

        FlowBuilder
        - Step, Flow, JobExecutionDecider 등 타입에 따라 다른 State 저장
        - StepState, FlowState, DecisionState

        StateTransition
        - State state    : 현재 State
        - String pattern : on() Transition
        - String next    : 다음 State

        - SimpleFlow 를 구성하고 있는 모든 Step 들이 Transition 에 따라 분기되어 실행
        - SimpleFlow 내 SimpleFlow 를 2중, 3중으로 중첩해서 구성할 수 있다.
        - Transition 은 구체적인 것 부터 그렇지 않은 순서로 적용 된다.

        State
        - Step, Flow, JobExecutionDecider 의 각 요소들을 저장
        - Flow 를 구성하면 내부적으로 생성되어 transition 과 연동
        - handle() 메소드 실행 수 FlowExecutionState 반환
            - on(pattern) 의 pattern 값과 매칭여부 판단
            - 마지막 실행 상태가 FlowJob 의 최종 상태가 됨
        - 현재 State 실행이 완료되면 다음 State 실행
        - 실행 순서는 순차적 개념이 아닌 설정 및 조건에 따라 결정
        - State 가 null 이거나 실행 불가능한 상태면 종료

        - SimpleFlow 는 StateMap 에 저장되어 있는 모든 State 들의 handle 메소드를 호출해서 모든 Step 들이 실행 되도록 한다.
        - SimpleFlow 는 현재 호출되는 State 가 어떤 타입인지와 상관없이 handle 메소드를 실행하고 상태값을 얻어온다.(상태패턴)
        - StateMap 은 StateTransition 객체로부터 State 객체의 정보를 참조해서 매핑함.

     */
    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                    .on("COMPLETED")
                    .to(step2())
                .from(step1())
                    .on("FAILED")
                    .to(flow())
                .end()
                .build();
    }

    @Bean
    public Flow flow() {

        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow1");

        flowBuilder
                .start(step2())
                .on("*")
                .to(step3())
                .end();

        return flowBuilder.build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 was executed");
                    throw new RuntimeException("step2 was failed");
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
