package io.springbatch.basic.flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ScopeConfiguration2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*
        1. Proxy 객체 생성
            - @JobScope, @StepScope 어노테이션이 붙은 빈 선언은 내부적으로 빈의 Proxy 객체가 생성
                - @JobScope
                    - @Scope(value="job", proxyMode=ScopedProxyMode.TARGET_CLASS)
                - @StepScope
                    - @Scope(value="step", proxyMode=ScopedProxyMode.TARGET_CLASS)

            - Job 실행 시 Proxy 객체가 실제 빈을 호출해서 해당 메소드를 실행 시키는 구조

        2. JobScope, StepScope
            - Proxy 객체의 실제 대상이 되는 Bean 을 등록, 해제하는 역할
            - 실제 빈을 저장하고 있는 JobContext, StepContext 를 가지고 있다.

        3. JobContext, StepContext
            - 스프링 컨테이너에서 생성된 빈을 저장하는 컨텍스트 역할
            - Job 의 실행 시점에서 프록시 객체가 실제 빈을 참조할 때 사용
     */
    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1(null))
                .next(step2())
                .listener(new CustomJobListener())
                .build();
    }

    @Bean
    @JobScope
    public Step step1(@Value("#{jobParameters['message']}") String message) {

        System.out.println("message = " + message);

        return stepBuilderFactory.get("step1")
                .tasklet(tasklet1(null))
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(tasklet2(null))
                .listener(new CustomStepListener())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet tasklet1(@Value("#{jobExecutionContext['name']}") String name) {

        System.out.println("name = " + name);

        return ((stepContribution, chunkContext) -> {
            System.out.println("tasklet1 was executed");
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    @StepScope
    public Tasklet tasklet2(@Value("#{stepExecutionContext['name2']}") String name2) {

        System.out.println("name2 = " + name2);

        return ((stepContribution, chunkContext) -> {
            System.out.println("tasklet2 was executed");
            return RepeatStatus.FINISHED;
        });
    }

}
