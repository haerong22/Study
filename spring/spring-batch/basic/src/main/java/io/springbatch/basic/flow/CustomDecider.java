package io.springbatch.basic.flow;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import java.util.Random;

public class CustomDecider implements JobExecutionDecider {


    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

        Random random = new Random();
        int count = random.nextInt(100);

        return count % 2 == 0 ?
                new FlowExecutionStatus("EVEN") : new FlowExecutionStatus("ODD");
    }
}
