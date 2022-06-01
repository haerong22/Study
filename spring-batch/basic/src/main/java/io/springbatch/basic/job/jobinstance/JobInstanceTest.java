package io.springbatch.basic.job.jobinstance;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

//@Component
@RequiredArgsConstructor
public class JobInstanceTest implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /*
            Job + JobParameters 를 가지고 JobInstance 를 생성
            Job + JobParameters 가 같을 경우 에러 발생

            BATCH_JOB_INSTANCE 테이블에 매핑 된다.
            JOB_NAME(job), JOB_KEY(jobParameter 해시값) 이 저장(중복 불가)
         */
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user2")
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
