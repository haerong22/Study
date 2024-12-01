package org.example.trxbatch.job.monthlytrxreport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.example.trxbatch.exception.TrxBatchNotSupportedException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyTrxReportClassifier implements Classifier<CustomerMonthlyTrxReport, ItemWriter<? super CustomerMonthlyTrxReport>> {

    private final MonthlyTrxReportViaPostWriter monthlyTrxReportViaPostWriter;
    private final MonthlyTrxReportViaAppMessengerWriter monthlyTrxReportViaAppMessengerWriter;
    private final MonthlyTrxReportViaEmailWriter monthlyTrxReportViaEmailWriter;

    @Override
    public ItemWriter<? super CustomerMonthlyTrxReport> classify(CustomerMonthlyTrxReport customerMonthlyTrxReport) {
        return switch (customerMonthlyTrxReport.getChannel()) {
            case APP_MESSAGE -> monthlyTrxReportViaAppMessengerWriter;
            case EMAIL -> monthlyTrxReportViaEmailWriter;
            case POST -> monthlyTrxReportViaPostWriter;
            default ->
                    throw new TrxBatchNotSupportedException("Unsupported channel: " + customerMonthlyTrxReport.getChannel());
        };
    }
}
