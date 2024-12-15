package org.example.trxbatch.repository;

import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

import static org.example.trxbatch.generated.Tables.MONTHLY_TRX_HEAVY_CUSTOMER;
import static org.jooq.impl.DSL.count;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("dbtest")
class MonthlyTrxHeavyCustomerRepositoryDbIntTest {

    @Autowired
    private DSLContext trxBatchDsl;

    @Autowired
    private MonthlyTrxHeavyCustomerRepository monthlyTrxHeavyCustomerRepository;

    @Test
    void batchInsertMonthlyTrxReportSuccess() {
        // given
        List<Long> heavyCustomerIds = List.of(1L, 2L);

        // When
        monthlyTrxHeavyCustomerRepository.batchInsert(heavyCustomerIds, YearMonth.of(2020, 1));

        // Then
        Integer count = trxBatchDsl.select(count())
                                   .from(MONTHLY_TRX_HEAVY_CUSTOMER)
                                   .where(MONTHLY_TRX_HEAVY_CUSTOMER.TRX_YEAR_MONTH.eq("2020-01"))
                                   .fetchOne()
                                   .value1();
        assertEquals(2, count);
    }

}