package org.example.trxbatch.job.monthlytrxreport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@SpringBatchTest
class MonthlyTrxReportJobConfigSpringTest {


    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @BeforeEach
    public void setup(@Autowired Job monthlyTrxReportJob) {
        jobLauncherTestUtils.setJob(monthlyTrxReportJob);
    }

    @Test
    void monthlyTrxReportJob() throws Exception {
        // given
        JobParameters jobParameters =
                jobLauncherTestUtils.getUniqueJobParametersBuilder().addString("targetYearMonth", "2024-01").toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        // cleanup
        jobRepositoryTestUtils.removeJobExecution(jobExecution);
    }

    @Test
    void monthlyTrxReportJobWithoutYearMonth() {
        // given
        JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParametersBuilder().toJobParameters();

        // expect
        assertThrows(JobParametersInvalidException.class, () -> {
            jobLauncherTestUtils.launchJob(jobParameters);
        });
    }

    @Test
    void monthlyTrxReportJobWithEmptyYearMonth() {
        // given
        JobParameters jobParameters =
                jobLauncherTestUtils.getUniqueJobParametersBuilder().addString("targetYearMonth", "").toJobParameters();

        // expect
        assertThrows(JobParametersInvalidException.class, () -> {
            jobLauncherTestUtils.launchJob(jobParameters);
        });
    }


    @Test
    void monthlyTrxReportJobWithInvalidYearMonth() {
        // given
        JobParameters jobParameters =
                jobLauncherTestUtils.getUniqueJobParametersBuilder().addString("targetYearMonth", "2024-13").toJobParameters();

        // expect
        assertThrows(JobParametersInvalidException.class, () -> {
            jobLauncherTestUtils.launchJob(jobParameters);
        });
    }

}