package org.example.trxbatch.repository;

import org.example.trxbatch.dto.MonthlyTrxSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("dbtest")
@SpringBootTest
@Transactional
class AppMessageRepositoryDbIntTest {

    @Autowired
    private AppMessageRepository appMessageRepository;

    @Test
    void batchInsertMonthlyTrxReportSuccess() {
        // given
        LocalDate sendDate = LocalDate.of(2024, 12, 10);
        List<MonthlyTrxSummary> summaries = List.of(
                new MonthlyTrxSummary(1L, 10, BigInteger.valueOf(1000)),
                new MonthlyTrxSummary(2L, 15, BigInteger.valueOf(1500))
        );

        // when
        int inserted = appMessageRepository.batchInsertMonthlyTrxReport(2024, 11, sendDate, summaries);

        // then
        assertEquals(2, inserted);
    }
}