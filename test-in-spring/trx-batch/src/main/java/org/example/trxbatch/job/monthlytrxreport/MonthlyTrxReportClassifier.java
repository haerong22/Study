package org.example.trxbatch.job.monthlytrxreport;

import lombok.extern.slf4j.Slf4j;
import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MonthlyTrxReportClassifier implements Classifier<CustomerMonthlyTrxReport, ItemWriter<? super CustomerMonthlyTrxReport>> {

    @Override
    public ItemWriter<? super CustomerMonthlyTrxReport> classify(CustomerMonthlyTrxReport customerMonthlyTrxReport) {
        // TODO
        return null;
    }
}
