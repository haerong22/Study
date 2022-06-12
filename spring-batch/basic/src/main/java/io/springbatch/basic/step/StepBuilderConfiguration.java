package io.springbatch.basic.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StepBuilderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*
        StepBuilderFactory
        - StepBuilder 를 생성하는 팩토리 클래스
        - get 메소드를 이용하여 Step 생성
            - StepBuilderFactory.get("stepName")

        StepBuilder
        - Step 을 구성하는 설정 조건에 따라 하위 빌더 클래스를 생성하고 Step 생성을 위임
        1. TaskletStepBuilder
            - TaskletStep 을 생성하는 기본 빌더 클래스
        2. SimpleStepBuilder
            - TaskletStep 을 생성하며 내부적으로 청크기반의 작업을 처리하는 ChunkOrientedTasklet 클래스 생성
        3. PartitionStepBuilder
            - PartitionStep 을 생성하며 멀티 쓰레드 방식으로 Job 을 실행
        4. JobStepBuilder
            - JobStep 을 생성하며 Step 안에서 Job 을 실행
        5. FlowStepBuilder
            - FlowStep 을 생성하며 Step 안에서 Flow 를 실행
     */
    @Bean
    public Job batchJob1() {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .next(step2())
                .next(step3())
                .next(step4())
                .next(step5())
                .build();
    }

    // TaskletStepBuilder
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // SimpleStepBuilder
    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<String, String>chunk(3)
                .reader(new ItemReader<String>() {
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        return null;
                    }
                })
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        return null;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> items) throws Exception {

                    }
                })
                .build();
    }

    // PartitionStepBuilder
    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .partitioner(step1())
                .gridSize(2)
                .build();
    }

    // JobStepBuilder
    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                .job(job())
                .build();
    }

    // FlowStepBuilder
    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
                .flow(flow())
                .build();
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    @Bean
    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(step2()).end();
        return flowBuilder.build();
    }
}
