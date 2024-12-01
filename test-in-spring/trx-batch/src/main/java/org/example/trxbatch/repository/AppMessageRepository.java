package org.example.trxbatch.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trxbatch.dto.MonthlyTrxSummary;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.example.trxbatch.generated.tables.AppMessage.APP_MESSAGE;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AppMessageRepository {

    private static final String INITIAL_STATUS = "CREATED";
    private static final long MONTHLY_TRX_REPORT_TEMPLATE_ID = 245L;

    private final DSLContext trxBatchDsl;


    public int batchInsertMonthlyTrxReport(int year, int month, LocalDate sendDate, List<MonthlyTrxSummary> summaries) {

        return trxBatchDsl
                .insertInto(APP_MESSAGE)
                .columns(APP_MESSAGE.CUSTOMER_ID,
                        APP_MESSAGE.TEMPLATE_ID,
                        APP_MESSAGE.SEND_DATE,
                        APP_MESSAGE.STATUS,
                        APP_MESSAGE.VALUE1,
                        APP_MESSAGE.VALUE2,
                        APP_MESSAGE.VALUE3,
                        APP_MESSAGE.VALUE4)
                .valuesOfRows(summaries.stream()
                        .map(summary -> DSL.row(summary.customerId(),
                                MONTHLY_TRX_REPORT_TEMPLATE_ID,
                                sendDate,
                                INITIAL_STATUS,
                                String.valueOf(year),
                                String.valueOf(month),
                                String.valueOf(summary.trxCount()),
                                String.valueOf(summary.trxAmountSum())))
                        .toList())
                .execute();
    }
}
