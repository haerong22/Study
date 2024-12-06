package org.example.trxbatch.repository;

import org.example.trxbatch.dto.enums.ReportChannel;
import org.example.trxbatch.exception.TrxBatchCsvWriteException;
import org.example.trxbatch.exception.TrxBatchEmailServerProcessException;
import org.example.trxbatch.exception.TrxBatchException;
import org.example.trxbatch.generated.tables.records.MonthlyTrxReportResultRecord;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

import static org.example.trxbatch.exception.enums.TrxBatchExceptionCode.EMAIL_SERVER_PROCESS_ERROR;
import static org.example.trxbatch.generated.tables.MonthlyTrxReportResult.MONTHLY_TRX_REPORT_RESULT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles("dbtest")
class MonthlyTrxReportResultRepositoryTest {
    @Autowired
    private DSLContext trxBatchDsl;

    @Autowired
    private MonthlyTrxReportResultRepository monthlyTrxReportResultRepository;

    @Test
    void testBatchInsertSuccessResults() {
        // given
        List<Long> customerIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L);
        YearMonth targetYearMonth = YearMonth.of(2020, 1);

        // when
        monthlyTrxReportResultRepository.batchInsertSuccessMonthlyTrxReportResult(
                customerIds,
                targetYearMonth,
                ReportChannel.APP_MESSAGE
        );

        // then
        List<MonthlyTrxReportResultRecord> rows = trxBatchDsl
                .select()
                .from(MONTHLY_TRX_REPORT_RESULT)
                .where(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH.eq("2020-01"))
                .and(MONTHLY_TRX_REPORT_RESULT.CHANNEL.eq(ReportChannel.APP_MESSAGE.name()))
                .fetchInto(MonthlyTrxReportResultRecord.class);

        assertEquals(15, rows.size());
        assertTrue(rows.stream()
                .allMatch(row -> row.get(MONTHLY_TRX_REPORT_RESULT.STATUS)
                        .equals(MonthlyTrxReportResultRepository.Status.SUCCESS.name())));
        assertTrue(rows.stream().allMatch(row -> row.get(MONTHLY_TRX_REPORT_RESULT.FAIL_REASON) == null));
        assertTrue(rows.stream().allMatch(row -> row.get(MONTHLY_TRX_REPORT_RESULT.FAIL_DETAIL) == null));
    }

    @Test
    void testBatchInsertFailResults() {
        List<Long> customerIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L);
        YearMonth targetYearMonth = YearMonth.of(2020, 1);
        TrxBatchException failCause = new TrxBatchEmailServerProcessException("exception-message");

        // when
        monthlyTrxReportResultRepository.batchInsertFailMonthlyTrxReportResult(
                customerIds,
                targetYearMonth,
                ReportChannel.APP_MESSAGE,
                failCause
        );

        // then
        List<MonthlyTrxReportResultRecord> rows = trxBatchDsl
                .select()
                .from(MONTHLY_TRX_REPORT_RESULT)
                .where(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH.eq("2020-01"))
                .and(MONTHLY_TRX_REPORT_RESULT.CHANNEL.eq(ReportChannel.APP_MESSAGE.name()))
                .fetchInto(MonthlyTrxReportResultRecord.class);

        assertEquals(15, rows.size());
        assertTrue(rows.stream()
                .allMatch(row -> row.get(MONTHLY_TRX_REPORT_RESULT.STATUS)
                        .equals(MonthlyTrxReportResultRepository.Status.FAIL.name())));
        assertTrue(rows.stream()
                .allMatch(row -> row.get(MONTHLY_TRX_REPORT_RESULT.FAIL_REASON)
                        .equals(EMAIL_SERVER_PROCESS_ERROR.getDbCode())));
        assertTrue(rows.stream().allMatch(row -> row.get(MONTHLY_TRX_REPORT_RESULT.FAIL_DETAIL).equals("exception-message")));
    }

    @Test
    void testInsertFailResult() {
        // when
        monthlyTrxReportResultRepository.insertFailMonthlyTrxReportResult(
                1L,
                YearMonth.of(2020, 1),
                ReportChannel.POST,
                new TrxBatchCsvWriteException("exception-message")
        );

        // then
        List<MonthlyTrxReportResultRecord> rows = trxBatchDsl
                .select()
                .from(MONTHLY_TRX_REPORT_RESULT)
                .where(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH.eq("2020-01"))
                .and(MONTHLY_TRX_REPORT_RESULT.CHANNEL.eq(ReportChannel.POST.name()))
                .fetchInto(MonthlyTrxReportResultRecord.class);

        assertEquals(1, rows.size());
        assertEquals(MonthlyTrxReportResultRepository.Status.FAIL.name(), rows.getFirst().get(MONTHLY_TRX_REPORT_RESULT.STATUS));
        assertEquals(new TrxBatchCsvWriteException().getExceptionCode().getDbCode(),
                rows.getFirst().get(MONTHLY_TRX_REPORT_RESULT.FAIL_REASON));
        assertEquals("exception-message", rows.get(0).get(MONTHLY_TRX_REPORT_RESULT.FAIL_DETAIL));

    }
}