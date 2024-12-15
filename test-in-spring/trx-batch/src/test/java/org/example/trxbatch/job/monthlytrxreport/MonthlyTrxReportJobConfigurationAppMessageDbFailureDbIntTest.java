package org.example.trxbatch.job.monthlytrxreport;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.trxbatch.job.monthlytrxreport.testhelper.MonthlyTrxReportDataCleaner;
import org.example.trxbatch.repository.AppMessageRepository;
import org.example.trxbatch.service.email.MonthlyTrxReportBulkEmailService;
import org.example.trxbatch.service.email.dto.BulkReserveMonthlyTrxReportRequestDto;
import org.example.trxbatch.service.email.dto.BulkReserveResponseData;
import org.example.trxbatch.service.email.dto.EmailServerResponse;
import org.example.trxbatch.service.email.dto.enums.EmailServerResponseCode;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.example.trxbatch.job.monthlytrxreport.testhelper.MonthlyTrxReportJobDbIntTestDataVerificationHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@SpringBatchTest
@ActiveProfiles("dbtest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class MonthlyTrxReportJobConfigurationAppMessageDbFailureDbIntTest {

    @Autowired
    private DSLContext trxBatchDsl;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private MonthlyTrxReportDataCleaner monthlyTrxReportDataCleaner;

    @Autowired
    private MonthlyTrxReportBulkEmailService monthlyTrxReportBulkEmailService;

    @Value("${email-server.url}")
    private String emailServerAddress;

    @Value("${email-server.api-key}")
    private String emailServerApiKey;

    @Autowired
    ClientHttpRequestFactory defaultRequestFactory;

    @MockitoBean
    private AppMessageRepository appMessageRepository;

    private MockRestServiceServer server;

    @BeforeEach
    public void setUp(@Autowired Job monthlyTrxReportJob) {
        RestClient.Builder clientBuilder = RestClient.builder()
                .requestFactory(defaultRequestFactory)
                .baseUrl(emailServerAddress)
                .defaultHeader("X-Api-Key", emailServerApiKey);

        MockRestServiceServer.MockRestServiceServerBuilder serverBuilder = MockRestServiceServer.bindTo(clientBuilder);
        this.server = serverBuilder.build();
        ReflectionTestUtils.setField(monthlyTrxReportBulkEmailService, "emailServerRestClient", clientBuilder.build());

        jobLauncherTestUtils.setJob(monthlyTrxReportJob);

        // Clean up data before each test
        monthlyTrxReportDataCleaner.deleteAllApplicationDataCreatedDuringJobExecution();
    }

    @Test
    void testAppMessageDbNotRespondAtAll() throws Exception {
        // given
        JobParameters jobParameters =
                jobLauncherTestUtils.getUniqueJobParametersBuilder().addString("targetYearMonth", "2024-04").toJobParameters();


        // email-server always returns success
        server.expect(ExpectedCount.manyTimes(), requestTo("http://localhost:8181/bulk-reserve"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andRespond(request -> {
                    BulkReserveMonthlyTrxReportRequestDto requestBody =
                            objectMapper.readValue(request.getBody().toString(), BulkReserveMonthlyTrxReportRequestDto.class);

                    // get current time in millis
                    long randomId = System.currentTimeMillis();
                    int templateDataCount = requestBody.getTemplateData().size();
                    EmailServerResponse<BulkReserveResponseData> jsonResponse = new EmailServerResponse<>(
                            EmailServerResponseCode.SUCCESS,
                            "Success",
                            new BulkReserveResponseData(randomId, templateDataCount)
                    );

                    return withSuccess(objectMapper.writeValueAsString(jsonResponse), MediaType.APPLICATION_JSON)
                            .createResponse(request);
                });

        // Set up AppMessageRepository to always throw exception
        doThrow(new DataAccessException("App Message DB is locked"))
                .when(appMessageRepository).batchInsertMonthlyTrxReport(anyInt(), anyInt(), any(), any());

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        assertEquals(HEAVY_CUSTOMER_COUNT, getHeavyCustomerCount(trxBatchDsl));
        assertEquals(SUCCESSFUL_REPORT_SUCCESS_COUNT - SUCCESSFUL_REPORT_VIA_APP_MESSAGE_SUCCESS_COUNT, getReportSuccessCount(trxBatchDsl));
        assertEquals(SUCCESSFUL_REPORT_VIA_POST_SUCCESS_COUNT, getReportViaPostSuccessCount(trxBatchDsl));
        assertEquals(SUCCESSFUL_REPORT_VIA_EMAIL_SUCCESS_COUNT, getReportViaEmailSuccessCount(trxBatchDsl));
        assertEquals(0, getReportViaAppMessageSuccessCount(trxBatchDsl));
        assertEquals(0, getAppMessageDbStoredCount(trxBatchDsl));
    }

}