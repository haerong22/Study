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

//@Configuration
@RequiredArgsConstructor
public class SimpleFlowConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*
        <SimpleFlow>

        - 스프링 배치에서 제공하는 Flow 의 구현체로 각 요소(Step, Flow, JobExecutionDecider)들을 담고있는 State  를
          실행시키는 도메인 객체
        - FlowBuilder 를 사용해서 생성하며 Transition 과 조합하여 여러개의 Flow 및 중첩 Flow 를 만들어 Job 구성 가능

        <구조>

        Flow
        - getName()
            : Flow 이름 조회
        - State getState(String stateName)
            : State 명으로 State 타입 반환
        - FlowExecution start(FlowExecutor executor)
            : Flow 를 실행시키는 start 메소드. 인자로 FlowExecutor 를 넘겨주어 실행을 위임, 실행 후 FlowExecution 반환
        - FlowExecution resume(String stateName, FlowExecutor executor)
            : 다음에 실행할 State 를 구해서 FlowExecutor 에게 실행을 위임
        - Collection<State> getStates()
            : Flow 가 가지고 있는 모든 State 를 Collection 타입으로 반환

        SimpleFlow
        - String name
            : Flow 이름
        - State startState
            : State 들 중에서 처음 실행할 State
        - Map<String, Set<StateTransition>> transitionMap
            : State 명으로 매핑되어 있는 Set<StateTransition>
        - Map<String, State> stateMap
            : State 명으로 매핑되어 있는 State 객체
        - List<StateTransition> stateTransitions
            : State 와 Transition 정보를 가지고 있는 StateTransition 리스트
        - Comparator<StateTransition> stateTransitionComparator
     */
    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(flow())
                .next(step3())
                .end()
                .build();
    }

    @Bean
    public Flow flow() {

        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");

        flowBuilder.start(step1())
                .next(step2())
                .end();

        return flowBuilder.build();
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
