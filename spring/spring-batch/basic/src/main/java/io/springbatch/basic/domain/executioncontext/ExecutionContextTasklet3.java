package io.springbatch.basic.domain.executioncontext;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionContextTasklet3 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("step3 was executed");

        Object name = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("name");

        if (name == null) {
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("name", "user1");

            // 처음에는 name 이 null 이므로 예외 발생
            // 재실행 시에는 JobExecution 의 ExecutionContext 을 공유하므로
            // name 이 null 이 아니므로 예외 발생 하지 않고 수행 된다.
            throw new RuntimeException("step3 was failed");
        }

        return RepeatStatus.FINISHED;
    }
}
