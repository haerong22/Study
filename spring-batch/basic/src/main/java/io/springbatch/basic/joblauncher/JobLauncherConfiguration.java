package io.springbatch.basic.joblauncher;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    JobLauncher

    배치 Job 을 실행
    Job, JobParameters 를 인자로 받아 작업 수행 후 JobExecution 을 반환

    동기적 실행
        - taskExecutor -> SyncTaskExecutor (default)
        - JobExecution 을 획득하고 배치 처리를 최종 완료한 이후 JobExecution 을 반환
          (ExitStatus.FINISHED or FAILED )
        - 스케줄러에 의한 배치처리에 적합

    비동기적 실행
        - taskExecutor -> SimpleAsyncTaskExecutor
        - JobExecution 을 획득하고 바로 JobExecution 을 반환 후 배치처리 완료
          (ExitStatus.UNKNOWN)
        - Http 요청에 의한 배치처리에 적합합
 */
@Configuration
@RequiredArgsConstructor
public class JobLauncherConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    Thread.sleep(10000);
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
