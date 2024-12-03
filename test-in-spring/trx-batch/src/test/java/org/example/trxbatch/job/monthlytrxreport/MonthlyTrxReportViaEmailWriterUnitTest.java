package org.example.trxbatch.job.monthlytrxreport;

import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.example.trxbatch.dto.enums.TransactionType;
import org.example.trxbatch.service.email.MonthlyTrxReportBulkEmailService;
import org.example.trxbatch.service.email.dto.BulkReserveMonthlyTrxReportRequestDto;
import org.example.trxbatch.service.email.dto.BulkReserveMonthlyTrxReportRequestTemplateContent;
import org.example.trxbatch.service.email.dto.BulkReserveResponseData;
import org.example.trxbatch.service.email.dto.EmailServerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.Chunk;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import static org.example.trxbatch.job.monthlytrxreport.fixture.CustomerMonthlyTrxReportFixtures.FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_1_THREE_TRXS;
import static org.example.trxbatch.job.monthlytrxreport.fixture.CustomerMonthlyTrxReportFixtures.FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_2_THREE_TRXS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MonthlyTrxReportViaEmailWriterUnitTest {

    @Mock
    private MonthlyTrxReportBulkEmailService monthlyTrxReportBulkEmailService;

    private MonthlyTrxReportViaEmailWriter monthlyTrxReportViaEmailWriter;

    @BeforeEach
    void setUp() {
        monthlyTrxReportViaEmailWriter =
                new MonthlyTrxReportViaEmailWriter("2024-05", monthlyTrxReportBulkEmailService);
    }

    @Test
    void testHandleReportSuccess() throws Exception {
        // given
        Chunk<CustomerMonthlyTrxReport> chunk = new Chunk<>(
                FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_1_THREE_TRXS(),
                FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_2_THREE_TRXS()
        );

        // when
        EmailServerResponse<BulkReserveResponseData> mockResponse = new EmailServerResponse<>();
        when(monthlyTrxReportBulkEmailService.requestBulkReserve(any())).thenReturn(mockResponse);
        monthlyTrxReportViaEmailWriter.write(chunk);

        // then
        var captor = ArgumentCaptor.forClass(BulkReserveMonthlyTrxReportRequestDto.class);
        verify(monthlyTrxReportBulkEmailService).requestBulkReserve(captor.capture());

        BulkReserveMonthlyTrxReportRequestDto captorValue = captor.getValue();
        assertEquals(512, captorValue.getTemplateId());
        assertEquals(2, captorValue.getTemplateData().size());
        assertEquals(LocalDateTime.of(2024, 6, 10, 9, 0, 0), captorValue.getSendAt());

        LinkedHashMap<Long, BulkReserveMonthlyTrxReportRequestTemplateContent> templateDatas = captorValue.getTemplateData();

        assertEquals(3, templateDatas.get(1L).getMonthlyTrxReportRows().size());

        var monthlyTrxReportRowForCustomerOne = templateDatas.get(1L).getMonthlyTrxReportRows().get(0);
        assertEquals("acct**", monthlyTrxReportRowForCustomerOne.getAcctNo());
        assertEquals("ticker01", monthlyTrxReportRowForCustomerOne.getTickerNameKr());
        assertEquals("2024-05-01 10:20:30", monthlyTrxReportRowForCustomerOne.getTransactionAt());
        assertEquals(TransactionType.BUY.name(), monthlyTrxReportRowForCustomerOne.getType());
        assertEquals("1,000", monthlyTrxReportRowForCustomerOne.getPrice());
        assertEquals("10", monthlyTrxReportRowForCustomerOne.getQuantity());
        assertEquals("10,000", monthlyTrxReportRowForCustomerOne.getAmount());
        assertEquals("KRW", monthlyTrxReportRowForCustomerOne.getCurrency());

        monthlyTrxReportRowForCustomerOne = templateDatas.get(1L).getMonthlyTrxReportRows().get(1);
        assertEquals("acct**", monthlyTrxReportRowForCustomerOne.getAcctNo());
        assertEquals("ticker02", monthlyTrxReportRowForCustomerOne.getTickerNameKr());
        assertEquals("2024-05-01 10:20:30", monthlyTrxReportRowForCustomerOne.getTransactionAt());
        assertEquals(TransactionType.SELL.name(), monthlyTrxReportRowForCustomerOne.getType());
        assertEquals("2,000", monthlyTrxReportRowForCustomerOne.getPrice());
        assertEquals("20", monthlyTrxReportRowForCustomerOne.getQuantity());
        assertEquals("40,000", monthlyTrxReportRowForCustomerOne.getAmount());
        assertEquals("KRW", monthlyTrxReportRowForCustomerOne.getCurrency());

        monthlyTrxReportRowForCustomerOne = templateDatas.get(1L).getMonthlyTrxReportRows().get(2);
        assertEquals("acct**", monthlyTrxReportRowForCustomerOne.getAcctNo());
        assertEquals("ticker03", monthlyTrxReportRowForCustomerOne.getTickerNameKr());
        assertEquals("2024-05-01 10:20:30", monthlyTrxReportRowForCustomerOne.getTransactionAt());
        assertEquals(TransactionType.BUY.name(), monthlyTrxReportRowForCustomerOne.getType());
        assertEquals("3,000", monthlyTrxReportRowForCustomerOne.getPrice());
        assertEquals("30", monthlyTrxReportRowForCustomerOne.getQuantity());
        assertEquals("90,000", monthlyTrxReportRowForCustomerOne.getAmount());
        assertEquals("KRW", monthlyTrxReportRowForCustomerOne.getCurrency());

        assertEquals(3, templateDatas.get(2L).getMonthlyTrxReportRows().size());

        var monthlyTrxReportRowForCustomerTwo = templateDatas.get(2L).getMonthlyTrxReportRows().get(0);
        assertEquals("acct**", monthlyTrxReportRowForCustomerTwo.getAcctNo());
        assertEquals("ticker04", monthlyTrxReportRowForCustomerTwo.getTickerNameKr());
        assertEquals("2024-05-02 10:20:30", monthlyTrxReportRowForCustomerTwo.getTransactionAt());
        assertEquals(TransactionType.BUY.name(), monthlyTrxReportRowForCustomerTwo.getType());
        assertEquals("1,000", monthlyTrxReportRowForCustomerTwo.getPrice());
        assertEquals("10", monthlyTrxReportRowForCustomerTwo.getQuantity());
        assertEquals("10,000", monthlyTrxReportRowForCustomerTwo.getAmount());
        assertEquals("KRW", monthlyTrxReportRowForCustomerTwo.getCurrency());

        monthlyTrxReportRowForCustomerTwo = templateDatas.get(2L).getMonthlyTrxReportRows().get(1);
        assertEquals("acct**", monthlyTrxReportRowForCustomerTwo.getAcctNo());
        assertEquals("ticker05", monthlyTrxReportRowForCustomerTwo.getTickerNameKr());
        assertEquals("2024-05-02 10:20:30", monthlyTrxReportRowForCustomerTwo.getTransactionAt());
        assertEquals(TransactionType.SELL.name(), monthlyTrxReportRowForCustomerTwo.getType());
        assertEquals("2,000", monthlyTrxReportRowForCustomerTwo.getPrice());
        assertEquals("20", monthlyTrxReportRowForCustomerTwo.getQuantity());
        assertEquals("40,000", monthlyTrxReportRowForCustomerTwo.getAmount());
        assertEquals("KRW", monthlyTrxReportRowForCustomerTwo.getCurrency());

        monthlyTrxReportRowForCustomerTwo = templateDatas.get(2L).getMonthlyTrxReportRows().get(2);
        assertEquals("acct**", monthlyTrxReportRowForCustomerTwo.getAcctNo());
        assertEquals("ticker06", monthlyTrxReportRowForCustomerTwo.getTickerNameKr());
        assertEquals("2024-05-02 10:20:30", monthlyTrxReportRowForCustomerTwo.getTransactionAt());
        assertEquals(TransactionType.BUY.name(), monthlyTrxReportRowForCustomerTwo.getType());
        assertEquals("3,000", monthlyTrxReportRowForCustomerTwo.getPrice());
        assertEquals("30", monthlyTrxReportRowForCustomerTwo.getQuantity());
        assertEquals("90,000", monthlyTrxReportRowForCustomerTwo.getAmount());
        assertEquals("KRW", monthlyTrxReportRowForCustomerTwo.getCurrency());
    }
}