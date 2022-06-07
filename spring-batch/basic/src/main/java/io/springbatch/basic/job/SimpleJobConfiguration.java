package io.springbatch.basic.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SimpleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*
        SimpleJob 은 Step 을 실행시키는 Job 구현체 SimpleJobBuilder 에 의해 생성
        여러 단계의 Step 으로 구성할 수 있고 순차적으로 실행
        모든 Step 이 성공적으로 완료 되어야 Job 이 완료
        마지막 실행한 Step 의 BatchStatus 가 Job 의 최종 BatchStatus
        중간에 Step 이 실패할 경우 다음 Step 은 실행 되지 않음
    */
    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")               // 스프링 배치가 Job 을 실행시킬 때 참조하는 Job 이름, JobBuilder 생성
                .start(step1())                                 // 처음 실행할 Step 설정, SimpleJobBuilder 반환
                .next(step2())                                  // 다음에 실행할 Step 설정, 모든 next() 의 Step 이 종료되면 Job 종료
                .incrementer(new RunIdIncrementer())            // JobParameter 의 값을 자동으로 증가해 주는 JobParametersIncrementer 설정
                .preventRestart()                               // Job 의 재시작 가능 여부 설정 기본 true
                .validator(new DefaultJobParametersValidator()) // JobParameters 를 실행하기 전 검증
                .listener(new JobExecutionListenerSupport())    // Job 라이프 사이클 특정 시점에 콜백 받도록 리스너 설정
                .build();                                       // SimpleJob 생성
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
}
