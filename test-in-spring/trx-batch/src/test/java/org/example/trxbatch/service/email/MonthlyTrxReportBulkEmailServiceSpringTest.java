package org.example.trxbatch.service.email;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.trxbatch.config.RestClientConfig;
import org.example.trxbatch.dto.enums.TransactionType;
import org.example.trxbatch.service.email.dto.BulkReserveMonthlyTrxReportRequestDto;
import org.example.trxbatch.service.email.dto.BulkReserveMonthlyTrxReportRequestTemplateContent;
import org.example.trxbatch.service.email.dto.BulkReserveResponseData;
import org.example.trxbatch.service.email.dto.EmailServerResponse;
import org.example.trxbatch.service.email.dto.enums.EmailServerResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(components = {MonthlyTrxReportBulkEmailService.class, RestClientConfig.class})
class MonthlyTrxReportBulkEmailServiceSpringTest {

    @Autowired
    private ObjectMapper objectMapper;

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getIntegerInstance(Locale.ENGLISH);

    private MockRestServiceServer server;

    private MonthlyTrxReportBulkEmailService monthlyTrxReportBulkEmailService;

    @BeforeEach
    void setUp() {
        RestClient.Builder clientBuilder = RestClient.builder()
                .baseUrl("http://localhost:8181")
                .defaultHeader("X-Api-Key", "DUMMY-KEY");
        MockRestServiceServer.MockRestServiceServerBuilder serverBuilder = MockRestServiceServer.bindTo(clientBuilder);

        this.server = serverBuilder.build();
        this.monthlyTrxReportBulkEmailService = new MonthlyTrxReportBulkEmailService(clientBuilder.build());
    }

    @Test
    void testRequestBulkReserveSuccess() throws JsonProcessingException {
        // given
        var customerOneData = new BulkReserveMonthlyTrxReportRequestTemplateContent();
        customerOneData.setMonthlyTrxReportRows(
                List.of(
                        createSingleRowOfBulkReserveRequestTemplateContent(1),
                        createSingleRowOfBulkReserveRequestTemplateContent(2)
                )
        );

        var customerTwoData = new BulkReserveMonthlyTrxReportRequestTemplateContent();
        customerTwoData.setMonthlyTrxReportRows(
                List.of(
                        createSingleRowOfBulkReserveRequestTemplateContent(3),
                        createSingleRowOfBulkReserveRequestTemplateContent(4)
                )
        );

        LinkedHashMap<Long, BulkReserveMonthlyTrxReportRequestTemplateContent> templateData = new LinkedHashMap<>();
        templateData.put(1L, customerOneData);
        templateData.put(2L, customerTwoData);

        BulkReserveMonthlyTrxReportRequestDto requestDto = new BulkReserveMonthlyTrxReportRequestDto();
        requestDto.setTemplateData(templateData);
        requestDto.setSendAt(LocalDateTime.now());

        var mockResponse =
                new EmailServerResponse<>(EmailServerResponseCode.SUCCESS, "Success", new BulkReserveResponseData(15369L, 4));

        this.server
                .expect(requestTo("http://localhost:8181/bulk-reserve"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("X-Api-Key", "DUMMY-KEY"))
                .andExpect(jsonPath("$.templateData.1.tableRows[0].col1").value("acct1"))
                .andExpect(jsonPath("$.templateData.1.tableRows[0].col2").value("ticker1"))
                .andExpect(jsonPath("$.templateData.1.tableRows[0].col3").value("2023-05-01 00:00:01"))
                .andExpect(jsonPath("$.templateData.1.tableRows[0].col4").value("SELL"))
                .andExpect(jsonPath("$.templateData.1.tableRows[0].col5").value(10))
                .andExpect(jsonPath("$.templateData.1.tableRows[0].col6").value(10))
                .andExpect(jsonPath("$.templateData.1.tableRows[0].col7").value(100))
                .andExpect(jsonPath("$.templateData.1.tableRows[0].col8").value("KRW"))
                .andExpect(jsonPath("$.templateData.1.tableRows[1].col1").value("acct2"))
                .andExpect(jsonPath("$.templateData.1.tableRows[1].col2").value("ticker2"))
                .andExpect(jsonPath("$.templateData.1.tableRows[1].col3").value("2023-05-01 00:00:02"))
                .andExpect(jsonPath("$.templateData.1.tableRows[1].col4").value("BUY"))
                .andExpect(jsonPath("$.templateData.1.tableRows[1].col5").value(20))
                .andExpect(jsonPath("$.templateData.1.tableRows[1].col6").value(10))
                .andExpect(jsonPath("$.templateData.1.tableRows[1].col7").value(200))
                .andExpect(jsonPath("$.templateData.1.tableRows[1].col8").value("KRW"))
                .andExpect(jsonPath("$.templateData.2.tableRows[0].col1").value("acct3"))
                .andExpect(jsonPath("$.templateData.2.tableRows[0].col2").value("ticker3"))
                .andExpect(jsonPath("$.templateData.2.tableRows[0].col3").value("2023-05-01 00:00:03"))
                .andExpect(jsonPath("$.templateData.2.tableRows[0].col4").value("SELL"))
                .andExpect(jsonPath("$.templateData.2.tableRows[0].col5").value(30))
                .andExpect(jsonPath("$.templateData.2.tableRows[0].col6").value(10))
                .andExpect(jsonPath("$.templateData.2.tableRows[0].col7").value(300))
                .andExpect(jsonPath("$.templateData.2.tableRows[0].col8").value("KRW"))
                .andExpect(jsonPath("$.templateData.2.tableRows[1].col1").value("acct4"))
                .andExpect(jsonPath("$.templateData.2.tableRows[1].col2").value("ticker4"))
                .andExpect(jsonPath("$.templateData.2.tableRows[1].col3").value("2023-05-01 00:00:04"))
                .andExpect(jsonPath("$.templateData.2.tableRows[1].col4").value("BUY"))
                .andExpect(jsonPath("$.templateData.2.tableRows[1].col5").value(40))
                .andExpect(jsonPath("$.templateData.2.tableRows[1].col6").value(10))
                .andExpect(jsonPath("$.templateData.2.tableRows[1].col7").value(400))
                .andExpect(jsonPath("$.templateData.2.tableRows[1].col8").value("KRW"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(mockResponse), MediaType.APPLICATION_JSON));

        // when
        EmailServerResponse<BulkReserveResponseData> receivedResponse = monthlyTrxReportBulkEmailService.requestBulkReserve(requestDto);

        // then
        this.server.verify();
        assertEquals(mockResponse, receivedResponse);

    }

    private static BulkReserveMonthlyTrxReportRequestTemplateContent.MonthlyTrxReportRow createSingleRowOfBulkReserveRequestTemplateContent(
            long id
    ) {
        var row = new BulkReserveMonthlyTrxReportRequestTemplateContent.MonthlyTrxReportRow();
        row.setAcctNo("acct" + id);
        row.setTickerNameKr("ticker" + id);
        row.setTransactionAt("2023-05-01 00:00:0" + id % 10);
        row.setType(id % 2 == 0 ? TransactionType.BUY.name() : TransactionType.SELL.name());
        var price = 10L * id;
        var quantity = 10L;
        row.setPrice(NUMBER_FORMAT.format(price));
        row.setQuantity(NUMBER_FORMAT.format(quantity));
        row.setAmount(NUMBER_FORMAT.format(BigInteger.valueOf(price * quantity)));
        row.setCurrency("KRW");
        return row;
    }

}