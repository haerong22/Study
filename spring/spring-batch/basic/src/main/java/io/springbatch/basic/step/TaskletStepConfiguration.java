package io.springbatch.basic.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

//@Configuration
@RequiredArgsConstructor
public class TaskletStepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .start(batchStep())
                .next(taskStep())
                .next(chunkStep())
                .build();
    }

    /*
        TaskletStep
        - 스프링 배치에서 제공하는 Step 의 구현체로서 Tasklet 을 실행시키는 도메인 객체
        - RepeatTemplate 를 사용해서 Tasklet 의 구문을 트랜잭션 경계 내에서 반복해서 실행
        - Task 기반과 Chunk 기반으로 나누어서 Tasklet 을 실행

        Task vs Chunk

        Chunk 기반
        - 하나의 큰 덩어리를 n개씩 나누어서 실행한다는 의미로 대량 처리를 하는 경우 효과적으로 설계
        - ItemReader, ItemProcessor, ItemWriter 를 사용하며 청크 기반 전용 Tasklet 인 ChunkOrientedTasklet 구현체 제공
        - Job -> TaskletStep -> RepeatTemplate -> ChunkOrientedTasklet -> ItemReader, ItemProcessor, ItemWriter

        Task 기반
        - ItemReader 와 ItemWriter 와 같은 청크 기반의 작업 보다 단일 작업 기반으로 처리되는 것이 효율적인 경우
        - 주로 Tasklet 구현체를 만들어 사용
        - 대량 처리를 하는 경우 chunk 기반에 비해 더 복잡한 구현 필요
        - Job -> TaskletStep -> RepeatTemplate -> Tasklet -> Business Logic
     */
    @Bean
    public Step batchStep() {
        return stepBuilderFactory.get("batchStep")                  // StepBuilder 를 생성하는 팩토리, Step 의 이름을 매개변수로 받음
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("batchStep was executed");   // Tasklet 클래스 설정 이 메소드를 실행하면 TaskletStepBuilder 반환환
                   return RepeatStatus.FINISHED;
                })
                .startLimit(10)                                     // Step 의 실행 횟수를 설정, 초과시 오류, 기본값은 INTEGER.MAX_VALUE
                .allowStartIfComplete(true)                         // Step 의 성공, 실패와 관계없이 항상 Step 을 실행하기 위한 설정
                .listener(new StepExecutionListener() {             // Step 라이프 사이클의 특정 시점에 콜백 제공받도록 StepExecutionListener 설정
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        System.out.println("before batchStep");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return ExitStatus.COMPLETED;
                    }
                })
                .build();                                           // TaskletStep 을 생성
    }

    // Task 기반
    @Bean
    public Step taskStep() {
        return stepBuilderFactory.get("taskStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("taskStep was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // Chunk 기반
    @Bean
    public Step chunkStep() {
        return stepBuilderFactory.get("chunkStep")
                .<String, String>chunk(10)
                .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3", "item4", "item5")))
                .processor((ItemProcessor<String, String>) String::toUpperCase)
                .writer(items -> {
                    items.forEach(System.out::println);
                })
                .build();
    }
}
