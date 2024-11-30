package org.example.trxbatch.job.monthlytrxreport;

import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

import static org.example.trxbatch.job.monthlytrxreport.MonthlyTrxReportJobConfig.JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION;

public class MonthlyTrxReportViaAppMessengerWriter implements ItemWriter<CustomerMonthlyTrxReport> {

    public MonthlyTrxReportViaAppMessengerWriter(
            @Value(JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION) String targetYearMonth
    ) {
    }

    @Override
    public void write(Chunk<? extends CustomerMonthlyTrxReport> chunk) throws Exception {
        // TODO
    }
}
