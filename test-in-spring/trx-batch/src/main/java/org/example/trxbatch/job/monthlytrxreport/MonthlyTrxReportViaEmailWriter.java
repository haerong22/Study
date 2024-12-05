package org.example.trxbatch.job.monthlytrxreport;

import lombok.extern.slf4j.Slf4j;
import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.example.trxbatch.dto.enums.ReportChannel;
import org.example.trxbatch.exception.TrxBatchEmailServerCommunicationException;
import org.example.trxbatch.exception.TrxBatchEmailServerProcessException;
import org.example.trxbatch.repository.MonthlyTrxReportResultRepository;
import org.example.trxbatch.service.email.MonthlyTrxReportBulkEmailService;
import org.example.trxbatch.service.email.dto.BulkReserveMonthlyTrxReportRequestDto;
import org.example.trxbatch.service.email.dto.BulkReserveMonthlyTrxReportRequestTemplateContent;
import org.example.trxbatch.service.email.dto.BulkReserveResponseData;
import org.example.trxbatch.service.email.dto.EmailServerResponse;
import org.example.trxbatch.service.email.dto.enums.EmailServerResponseCode;
import org.example.trxbatch.util.MaskUtil;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.example.trxbatch.job.monthlytrxreport.MonthlyTrxReportJobConfig.JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION;

@Slf4j
@Component
@StepScope
public class MonthlyTrxReportViaEmailWriter implements ItemWriter<CustomerMonthlyTrxReport> {
    private static final int DAY_10 = 10;
    private static final int HOUR_9 = 9;
    private static final int MINUTE_00 = 0;

    private static final DateTimeFormatter TRANSACTION_AT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getIntegerInstance(Locale.ENGLISH);

    private final LocalDateTime sendAt;
    private final String targetYearMonthStr;

    private final MonthlyTrxReportBulkEmailService monthlyTrxReportBulkEmailService;
    private final MonthlyTrxReportResultRepository monthlyTrxReportResultRepository;

    public MonthlyTrxReportViaEmailWriter(
            @Value(JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION) String targetYearMonthstr,
            MonthlyTrxReportBulkEmailService monthlyTrxReportBulkEmailService,
            MonthlyTrxReportResultRepository monthlyTrxReportResultRepository
    ) {
        this.sendAt = decideSendingDateTime(targetYearMonthstr);
        this.targetYearMonthStr = targetYearMonthstr;
        this.monthlyTrxReportBulkEmailService = monthlyTrxReportBulkEmailService;
        this.monthlyTrxReportResultRepository = monthlyTrxReportResultRepository;
    }

    @Override
    public void write(Chunk<? extends CustomerMonthlyTrxReport> chunk) throws Exception {
        if (chunk.isEmpty()) {
            return;
        }

        var requestDto = new BulkReserveMonthlyTrxReportRequestDto();
        requestDto.setSendAt(sendAt);
        requestDto.setTemplateData(convertToMonthlyTrxReportTemplateData(chunk.getItems()));

        EmailServerResponse<BulkReserveResponseData> emailServerResponse;
        try {
            emailServerResponse = monthlyTrxReportBulkEmailService.requestBulkReserve(requestDto);
        } catch (TrxBatchEmailServerCommunicationException e) {
            handleEmailServerCommunicationException(chunk.getItems(), e);
            return;
        }

        if (emailServerResponse.getResponseCode() == EmailServerResponseCode.SUCCESS) {
            log.info("Succeeded bulk-reserve id: {}", emailServerResponse.getData().getId());
            handleSuccess(chunk.getItems());
        } else {
            handleUnsuccessfulEmailServerProcess(chunk.getItems(), emailServerResponse);
        }
    }

    private void handleSuccess(List<? extends CustomerMonthlyTrxReport> reports) {
        int i = monthlyTrxReportResultRepository.batchInsertSuccessMonthlyTrxReportResult(
                reports.stream().map(CustomerMonthlyTrxReport::getCustomerId).toList(),
                YearMonth.parse(targetYearMonthStr),
                ReportChannel.EMAIL
        );
        log.info("Inserted {} success records to monthlyTrxReportResultRepository", i);
    }


    private void handleEmailServerCommunicationException(
            List<? extends CustomerMonthlyTrxReport> items,
            TrxBatchEmailServerCommunicationException e
    ) {
        log.error("Communication with e-mail server failed. Failure will be recorded to DB", e);
        int i = monthlyTrxReportResultRepository.batchInsertFailMonthlyTrxReportResult(
                items.stream().map(CustomerMonthlyTrxReport::getCustomerId).toList(),
                YearMonth.parse(targetYearMonthStr),
                ReportChannel.EMAIL,
                e
        );
        log.info("Inserted {} fail records to monthlyTrxReportResultRepository", i);

    }


    private void handleUnsuccessfulEmailServerProcess(
            List<? extends CustomerMonthlyTrxReport> items,
            EmailServerResponse<BulkReserveResponseData> emailServerResponse
    ) {

        log.error("E-mail server returned {}. Failure will be recorded to DB of data", emailServerResponse.getResponseCode());

        int i = monthlyTrxReportResultRepository.batchInsertFailMonthlyTrxReportResult(
                items.stream().map(CustomerMonthlyTrxReport::getCustomerId).toList(),
                YearMonth.parse(targetYearMonthStr),
                ReportChannel.EMAIL,
                new TrxBatchEmailServerProcessException(emailServerResponse.getResponseMessage())
        );

        log.info("Inserted {} fail records to monthlyTrxReportResultRepository", i);
    }


    private LinkedHashMap<Long, BulkReserveMonthlyTrxReportRequestTemplateContent> convertToMonthlyTrxReportTemplateData(
            List<? extends CustomerMonthlyTrxReport> reports
    ) {

        LinkedHashMap<Long, BulkReserveMonthlyTrxReportRequestTemplateContent> templateData = new LinkedHashMap<>();
        reports.forEach(report -> {
            var templateContent = new BulkReserveMonthlyTrxReportRequestTemplateContent();
            templateContent.setMonthlyTrxReportRows(report.getCustomerMonthlyTrxReportDetails().stream().map(vo -> {
                var row = new BulkReserveMonthlyTrxReportRequestTemplateContent.MonthlyTrxReportRow();
                row.setAcctNo(MaskUtil.maskTailWithAsterisk(vo.getAcctNo(), 2));
                row.setTickerNameKr(vo.getTickerNameKr());
                row.setTransactionAt(vo.getTransactionAt().format(TRANSACTION_AT_FORMATTER));
                row.setType(vo.getType().name());
                row.setPrice(NUMBER_FORMAT.format(vo.getPrice()));
                row.setQuantity(NUMBER_FORMAT.format(vo.getQuantity()));
                row.setAmount(NUMBER_FORMAT.format(vo.getAmount()));
                row.setCurrency(vo.getCurrency());
                return row;
            }).collect(Collectors.toList()));
            templateData.putIfAbsent(report.getCustomerId(), templateContent);
        });
        return templateData;
    }

    private LocalDateTime decideSendingDateTime(String targetYearMonthString) {
        return YearMonth.parse(targetYearMonthString).plusMonths(1).atDay(DAY_10).atTime(HOUR_9, MINUTE_00);
    }
}
