package org.example.trxbatch.job.monthlytrxreport;

import lombok.extern.slf4j.Slf4j;
import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.example.trxbatch.dto.CustomerMonthlyTrxReportDetail;
import org.example.trxbatch.dto.MonthlyTrxSummary;
import org.example.trxbatch.repository.AppMessageRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.example.trxbatch.job.monthlytrxreport.MonthlyTrxReportJobConfig.JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION;

@Slf4j
@Component
@StepScope
public class MonthlyTrxReportViaAppMessengerWriter implements ItemWriter<CustomerMonthlyTrxReport> {

    private static final int DAY_10 = 10;
    private final int year;
    private final int month;
    private final LocalDate sendDate;

    private final AppMessageRepository appMessageRepository;

    public MonthlyTrxReportViaAppMessengerWriter(
            @Value(JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION) String targetYearMonthStr,
            AppMessageRepository appMessageRepository
    ) {
        YearMonth targetYearMonth = YearMonth.parse(targetYearMonthStr);
        this.year = targetYearMonth.getYear();
        this.month = targetYearMonth.getMonthValue();
        this.sendDate = targetYearMonth.plusMonths(1).atDay(DAY_10);
        this.appMessageRepository = appMessageRepository;
    }

    @Override
    public void write(Chunk<? extends CustomerMonthlyTrxReport> reports) throws Exception {
        if (reports.isEmpty()) {
            return;
        }

        List<MonthlyTrxSummary> summaries = reports.getItems().stream().map(report -> {
            List<CustomerMonthlyTrxReportDetail> details = report.getCustomerMonthlyTrxReportDetails();
            return new MonthlyTrxSummary(
                    report.getCustomerId(),
                    details.size(),
                    details.stream()
                            .map(CustomerMonthlyTrxReportDetail::getAmount)
                            .reduce(BigInteger.ZERO, BigInteger::add)
            );
        }).toList();

        appMessageRepository.batchInsertMonthlyTrxReport(year, month, sendDate, summaries);
    }
}
