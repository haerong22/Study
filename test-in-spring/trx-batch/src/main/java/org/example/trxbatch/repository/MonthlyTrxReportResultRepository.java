package org.example.trxbatch.repository;

import lombok.RequiredArgsConstructor;
import org.example.trxbatch.dto.enums.ReportChannel;
import org.example.trxbatch.exception.TrxBatchException;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.trxbatch.generated.tables.MonthlyTrxReportResult.MONTHLY_TRX_REPORT_RESULT;

@Repository
@RequiredArgsConstructor
public class MonthlyTrxReportResultRepository {

    enum Status {
        SUCCESS,
        FAIL,
    }

    private static final DateTimeFormatter TARGET_YEAR_MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");

    private final DSLContext trxBatchDsl;


    public int batchInsertSuccessMonthlyTrxReportResult(
            List<Long> customerIds,
            YearMonth targetYearMonth,
            ReportChannel channel
    ) {

        return trxBatchDsl
                .insertInto(MONTHLY_TRX_REPORT_RESULT)
                .columns(MONTHLY_TRX_REPORT_RESULT.CUSTOMER_ID,
                        MONTHLY_TRX_REPORT_RESULT.CHANNEL,
                        MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH,
                        MONTHLY_TRX_REPORT_RESULT.STATUS)
                .valuesOfRows(customerIds.stream()
                        .map(customerId -> DSL.row(
                                customerId,
                                channel.name(),
                                targetYearMonth.format(TARGET_YEAR_MONTH_FORMAT),
                                Status.SUCCESS.name())
                        )
                        .toList())
                .execute();
    }

    public int batchInsertFailMonthlyTrxReportResult(
            List<Long> customerIds,
            YearMonth targetYearMonth,
            ReportChannel channel,
            TrxBatchException failCause
    ) {

        return trxBatchDsl
                .insertInto(MONTHLY_TRX_REPORT_RESULT)
                .columns(MONTHLY_TRX_REPORT_RESULT.CUSTOMER_ID,
                        MONTHLY_TRX_REPORT_RESULT.CHANNEL,
                        MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH,
                        MONTHLY_TRX_REPORT_RESULT.STATUS,
                        MONTHLY_TRX_REPORT_RESULT.FAIL_REASON,
                        MONTHLY_TRX_REPORT_RESULT.FAIL_DETAIL)
                .valuesOfRows(customerIds.stream()
                        .map(customerId -> DSL.row(
                                customerId,
                                channel.name(),
                                targetYearMonth.format(TARGET_YEAR_MONTH_FORMAT),
                                Status.FAIL.name(),
                                failCause.getExceptionCode().getDbCode(),
                                failCause.getMessage())
                        )
                        .toList())
                .execute();
    }

    public int insertFailMonthlyTrxReportResult(
            Long customerId,
            YearMonth targetYearMonth,
            ReportChannel channel,
            TrxBatchException failCause
    ) {

        return trxBatchDsl
                .insertInto(MONTHLY_TRX_REPORT_RESULT)
                .set(MONTHLY_TRX_REPORT_RESULT.CUSTOMER_ID, customerId)
                .set(MONTHLY_TRX_REPORT_RESULT.CHANNEL, channel.name())
                .set(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH, targetYearMonth.format(TARGET_YEAR_MONTH_FORMAT))
                .set(MONTHLY_TRX_REPORT_RESULT.STATUS, Status.FAIL.name())
                .set(MONTHLY_TRX_REPORT_RESULT.FAIL_REASON, failCause.getExceptionCode().getDbCode())
                .set(MONTHLY_TRX_REPORT_RESULT.FAIL_DETAIL, failCause.getMessage())
                .execute();
    }

}