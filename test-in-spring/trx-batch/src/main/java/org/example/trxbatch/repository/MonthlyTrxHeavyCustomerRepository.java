package org.example.trxbatch.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.trxbatch.generated.Tables.MONTHLY_TRX_HEAVY_CUSTOMER;
import static org.jooq.impl.DSL.row;

@RequiredArgsConstructor
@Repository
public class MonthlyTrxHeavyCustomerRepository {

    static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    private final DSLContext trxBatchDsl;

    public void batchInsert(List<? extends Long> heavyCustomerIds, YearMonth trxYearMonth) {

        String formattedTrxYearMonth = trxYearMonth.format(YEAR_MONTH_FORMATTER);

        trxBatchDsl
                .insertInto(MONTHLY_TRX_HEAVY_CUSTOMER)
                .columns(MONTHLY_TRX_HEAVY_CUSTOMER.CUSTOMER_ID, MONTHLY_TRX_HEAVY_CUSTOMER.TRX_YEAR_MONTH)
                .valuesOfRows(
                        heavyCustomerIds.stream()
                                .map(heavyCustomerId -> row((Long) heavyCustomerId, formattedTrxYearMonth))
                                .toList())
                .execute();
    }
}