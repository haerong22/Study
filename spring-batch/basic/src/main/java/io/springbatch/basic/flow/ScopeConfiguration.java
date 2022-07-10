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
public class ScopeConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*
        1. Scope
            - 스프링 컨테이너 에서 빈이 관리되는 범위
            - singleton, prototype, request, session, application 있으며 기본은 singleton

        2. 스프링 배치 스코프
            @JobScope, @StepScope
            - Job 과 Step 의 빈 생성과 실행에 관여하는 스코프
            - 프록시 모드를 기본값으로 하는 스코프 - @Scope(value = "job", proxyMode = ScopedProxyMode.TARGET_CLASS)
            - 해당 스코프가 선언되면 빈의 생성이 어플리케이션 구동시점이 아닌 빈의 실행시점에 이루어진다.
                - @Values 를 주입해서 빈의 실행 시점에 값을 참조할 수 있으며 일종의 Lazy Binding 이 가능해진다.
                - @Value("#{jobParameters[파라미터명]}"),
                  @Value("#{jobExecutionContext[파라미터명]}"),
                  @Value("#{stepExecutionContext[파라미터명]}")
            - 프록시 모드로 빈이 선언되기 때문에 어플리케이션 구동시점에는 빈의 프록시 객체가 생성되어 실행 시점에 실제 빈을 호출.
            - 병렬처리 시 각 스레드마다 생성된 스코프 빈이 할당되기 때문에 스레드에 안전하게 실행 가능

        3. @JopScope
            - Step 선언문에 정의
            - @Value: jobParameter, jobExecutionContext 만 사용가능

        4. @StepScope
            - Tasklet 이나 ItemReader, ItemWriter, ItemProcessor 선언문에 정의
            - @Value: jobParameter, jobExecutionContext, stepExecutionContext 사용가능
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
