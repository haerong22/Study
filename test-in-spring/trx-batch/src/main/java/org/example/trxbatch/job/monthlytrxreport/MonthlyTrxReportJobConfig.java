package org.example.trxbatch.job.monthlytrxreport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MonthlyTrxReportJobConfig extends DefaultBatchConfiguration {

    private static final String JOB_NAME = "monthlyTrxReportJob";
    private static final String JOB_PARAM_TARGET_YEAR_MONTH = "targetYearMonth";
    public static final String JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION = "#{jobParameters['" + JOB_PARAM_TARGET_YEAR_MONTH + "']}";

    private final MonthlyTrxReportPagingItemReader monthlyTrxReportPagingItemReader;
    private final MonthlyTrxReportClassifier monthlyTrxReportClassifier;
    private final HeavyCustomerSeparationItemReader heavyCustomerSeparationItemReader;
    private final HeavyCustomerSeparationItemWriter heavyCustomerSeparationItemWriter;

    @Bean
    public Job monthlyTrxReportJob(
            JobRepository jobRepository,
            Step heavyCustomerSeparationStep,
            Step customerMonthlyTrxReportStep,
            Step heavyCustomerMonthlyTrxReportStep
    ) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .validator(validateTargetYearMonthParam())
                .start(heavyCustomerSeparationStep)
                .next(customerMonthlyTrxReportStep)
                .next(heavyCustomerMonthlyTrxReportStep)
                .build();
    }

    private JobParametersValidator validateTargetYearMonthParam() {
        return parameters -> {
            if (parameters == null || parameters.getString("targetYearMonth") == null) {
                throw new JobParametersInvalidException("Required parameter is missing: " + JOB_PARAM_TARGET_YEAR_MONTH);
            }

            try {
                YearMonth.parse(parameters.getString("targetYearMonth"));
            } catch (DateTimeParseException e) {
                throw new JobParametersInvalidException("Invalid parameter format: " + JOB_PARAM_TARGET_YEAR_MONTH);
            }
        };
    }

    @Bean
    public Step heavyCustomerSeparationStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("heavyCustomerSeparationStep", jobRepository)
                .<Long, Long>chunk(100, transactionManager)
                .reader(heavyCustomerSeparationItemReader)
                .writer(heavyCustomerSeparationItemWriter)
                .build();
    }

    @Bean
    public Step customerMonthlyTrxReportStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManger
    ) {
        return new StepBuilder("customerMonthlyTrxReportStep", jobRepository)
                .<CustomerMonthlyTrxReport, CustomerMonthlyTrxReport>chunk(100, transactionManger)
                .reader(monthlyTrxReportPagingItemReader)
                .writer(classifierCompositeItemWriter())
                .build();
    }

    @Bean
    public Step heavyCustomerMonthlyTrxReportStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("heavyCustomerMonthlyTrxReportStep", jobRepository)
                .tasklet(
                        (contribution, chunkContext) -> {
                            log.debug("Let's leave this part unimplemented!");
                            return null;
                        }, transactionManager
                )
                .build();
    }


    @Bean
    @StepScope
    public ClassifierCompositeItemWriter<CustomerMonthlyTrxReport> classifierCompositeItemWriter() {
        return new ClassifierCompositeItemWriterBuilder<CustomerMonthlyTrxReport>()
                .classifier(monthlyTrxReportClassifier)
                .build();
    }
}


