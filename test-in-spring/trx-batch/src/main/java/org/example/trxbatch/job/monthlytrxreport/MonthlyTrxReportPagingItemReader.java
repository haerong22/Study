package org.example.trxbatch.job.monthlytrxreport;

import lombok.extern.slf4j.Slf4j;
import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.example.trxbatch.job.monthlytrxreport.MonthlyTrxReportJobConfig.*;

@Slf4j
@Component
@StepScope
public class MonthlyTrxReportPagingItemReader extends AbstractPagingItemReader<CustomerMonthlyTrxReport> {

    public MonthlyTrxReportPagingItemReader(
            @Value(JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION) String targetYearMonth
    ) {
        // TODO
    }

    @Override
    protected void doReadPage() {
        // TODO
    }
}
