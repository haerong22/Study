package org.example.trxbatch.job.monthlytrxreport;

import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.example.trxbatch.dto.MonthlyTrxSummary;
import org.example.trxbatch.repository.AppMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.Chunk;

import java.math.BigInteger;
import java.time.YearMonth;
import java.util.List;

import static org.example.trxbatch.job.monthlytrxreport.fixture.CustomerMonthlyTrxReportFixtures.FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_1_THREE_TRXS;
import static org.example.trxbatch.job.monthlytrxreport.fixture.CustomerMonthlyTrxReportFixtures.FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_2_THREE_TRXS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MonthlyTrxReportViaAppMessengerWriterUnitTest {

    @Mock
    private AppMessageRepository appMessageRepository;

    private MonthlyTrxReportViaAppMessengerWriter writer;

    @BeforeEach
    void setUp() {
        writer = new MonthlyTrxReportViaAppMessengerWriter("2024-05", appMessageRepository);
    }

    @Test
    void testSuccess() throws Exception {
        // given
        Chunk<CustomerMonthlyTrxReport> chunk = new Chunk<>(
                List.of(
                        FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_1_THREE_TRXS(),
                        FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_2_THREE_TRXS()
                )
        );

        // when
        writer.write(chunk);

        // then
        ArgumentCaptor<List<MonthlyTrxSummary>> captor = ArgumentCaptor.forClass(List.class);

        verify(appMessageRepository, times(1)).batchInsertMonthlyTrxReport(
                eq(2024),
                eq(5),
                eq(YearMonth.of(2024, 6).atDay(10)),
                captor.capture()
        );
        List<MonthlyTrxSummary> summaries = captor.getValue();
        assertEquals(2, summaries.size());

        MonthlyTrxSummary summary1 = summaries.get(0);
        assertEquals(1L, summary1.customerId());
        assertEquals(3, summary1.trxCount());
        assertEquals(new BigInteger("140000"), summary1.trxAmountSum());

        MonthlyTrxSummary summary2 = summaries.get(1);
        assertEquals(2L, summary2.customerId());
        assertEquals(3, summary2.trxCount());
        assertEquals(new BigInteger("140000"), summary2.trxAmountSum());
    }

    @Test
    void testEmpty() throws Exception {
        // given
        Chunk<CustomerMonthlyTrxReport> chunk = new Chunk<>(List.of());

        // when
        writer.write(chunk);

        // then
        verify(appMessageRepository, never()).batchInsertMonthlyTrxReport(anyInt(), anyInt(), any(), any());
    }
}