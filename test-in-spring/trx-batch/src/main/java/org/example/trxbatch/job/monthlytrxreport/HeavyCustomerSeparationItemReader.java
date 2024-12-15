package org.example.trxbatch.job.monthlytrxreport;

import lombok.extern.slf4j.Slf4j;
import org.example.trxbatch.repository.MonthlyTrxReportRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.example.trxbatch.job.monthlytrxreport.MonthlyTrxReportJobConfig.JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION;

@Slf4j
@Component
@StepScope
public class HeavyCustomerSeparationItemReader extends AbstractPagingItemReader<Long> {
    private static final int DEFAULT_PAGE_SIZE = 100;
    private static final int HEAVY_CUSTOMER_THRESHOLD = 50;
    private final MonthlyTrxReportRepository monthlyTrxReportRepository;
    private final LocalDateTime from; // inclusive
    private final LocalDateTime before; // exclusive

    private Long lastCustomerId;

    public HeavyCustomerSeparationItemReader(
            @Value(JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION) String targetYearMonthString,
            MonthlyTrxReportRepository monthlyTrxReportRepository
    ) {
        YearMonth targetYearMonth = YearMonth.parse(targetYearMonthString);
        this.from = targetYearMonth.atDay(1).atStartOfDay();
        this.before = targetYearMonth.plusMonths(1).atDay(1).atStartOfDay();
        this.monthlyTrxReportRepository = monthlyTrxReportRepository;
        this.setPageSize(DEFAULT_PAGE_SIZE);
    }

    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }

        List<Long> heavyCustomerIds = monthlyTrxReportRepository.fetchHeavyCustomerIds(
                from,
                before,
                HEAVY_CUSTOMER_THRESHOLD,
                lastCustomerId,
                getPageSize()
        );

        if (!heavyCustomerIds.isEmpty()) {
            lastCustomerId = heavyCustomerIds.getLast();
        }

        this.results.addAll(heavyCustomerIds);
    }
}