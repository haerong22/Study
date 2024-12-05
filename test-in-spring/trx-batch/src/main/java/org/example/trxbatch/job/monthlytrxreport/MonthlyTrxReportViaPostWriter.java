package org.example.trxbatch.job.monthlytrxreport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.example.trxbatch.dto.enums.ReportChannel;
import org.example.trxbatch.dto.enums.TransactionType;
import org.example.trxbatch.exception.TrxBatchCsvWriteException;
import org.example.trxbatch.repository.MonthlyTrxReportResultRepository;
import org.example.trxbatch.util.MaskUtil;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.example.trxbatch.job.monthlytrxreport.MonthlyTrxReportJobConfig.JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION;

@Slf4j
@Component
@StepScope
public class MonthlyTrxReportViaPostWriter implements ItemWriter<CustomerMonthlyTrxReport> {

    @Value("${post-service.csv.save.path}")
    private String csvFileSavePath;

    private static final String CSV_FILE_NAME_FORMAT = "monthly-trx-report-%s-%s.csv";
    private static final DateTimeFormatter TRANSACTION_AT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getIntegerInstance(Locale.ENGLISH);

    private final String targetYearMonthStr;

    private final MonthlyTrxReportResultRepository monthlyTrxReportResultRepository;

    public MonthlyTrxReportViaPostWriter(
            @Value(JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION) String targetYearMonthStr,
            MonthlyTrxReportResultRepository monthlyTrxReportResultRepository
    ) {
        this.targetYearMonthStr = targetYearMonthStr;
        this.monthlyTrxReportResultRepository = monthlyTrxReportResultRepository;
    }

    @Override
    public void write(Chunk<? extends CustomerMonthlyTrxReport> reports) throws Exception {
        if (reports.isEmpty()) {
            return;
        }

        List<Long> successCustomerIds = new ArrayList<>();
        for (CustomerMonthlyTrxReport report : reports) {
            FlatFileItemWriter<MonthlyTrxReportRow> fileItemWriter = new FlatFileItemWriter<>();
            fileItemWriter.setResource(new FileSystemResource(decideTargetPath(report)));
            fileItemWriter.setLineAggregator(MonthlyTrxReportRow.getLineAggregator());
            try {
                fileItemWriter.afterPropertiesSet();
                fileItemWriter.open(new ExecutionContext());
                fileItemWriter.write(collectRows(report));
                successCustomerIds.add(report.getCustomerId());
            } catch (Exception e) {
                log.error("Failed to write CSV file for customer {}", report.getCustomerId(), e);
                handleFileWriteError(report.getCustomerId(), e);
            } finally {
                fileItemWriter.close();
            }
        }

        handleSuccessCases(successCustomerIds);
    }

    private void handleSuccessCases(List<Long> customerIds) {
        int i = monthlyTrxReportResultRepository.batchInsertSuccessMonthlyTrxReportResult(
                customerIds,
                YearMonth.parse(targetYearMonthStr),
                ReportChannel.POST
        );

        log.info("Inserted {} success records to monthlyTrxReportResultRepository", i);
    }


    private void handleFileWriteError(Long customerId, Exception e) {
        int i = monthlyTrxReportResultRepository.insertFailMonthlyTrxReportResult(
                customerId,
                YearMonth.parse(targetYearMonthStr),
                ReportChannel.POST,
                new TrxBatchCsvWriteException(e)
        );

        log.info("Inserted {} fail records to monthlyTrxReportResultRepository", i);
    }

    private static Chunk<MonthlyTrxReportRow> collectRows(CustomerMonthlyTrxReport item) {
        List<MonthlyTrxReportRow> rows = item.getCustomerMonthlyTrxReportDetails().stream().map(vo -> {
            MonthlyTrxReportRow row = new MonthlyTrxReportRow();
            row.setMaskedAcctNo(MaskUtil.maskTailWithAsterisk(vo.getAcctNo(), 2));
            row.setTickerNameKr(vo.getTickerNameKr());
            row.setFormattedTransactionDatetime(vo.getTransactionAt().format(TRANSACTION_AT_FORMAT));
            row.setType(vo.getType());
            row.setFormattedPrice(NUMBER_FORMAT.format(vo.getPrice()));
            row.setFormattedQuantity(NUMBER_FORMAT.format(vo.getQuantity()));
            row.setFormattedAmount(NUMBER_FORMAT.format(vo.getAmount()));
            row.setCurrency(vo.getCurrency());
            return row;
        }).toList();

        return new Chunk<>(rows);
    }


    private Path decideTargetPath(CustomerMonthlyTrxReport report) {
        String filename = String.format(CSV_FILE_NAME_FORMAT, targetYearMonthStr, report.getCustomerId());
        return Paths.get(csvFileSavePath, filename);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class MonthlyTrxReportRow {
        private String formattedTransactionDatetime;
        private String maskedAcctNo;
        private String tickerNameKr;
        private TransactionType type;
        private String formattedQuantity;
        private String formattedPrice;
        private String formattedAmount;
        private String currency;

        public static LineAggregator<MonthlyTrxReportRow> getLineAggregator() {
            CustomLineAggregator<MonthlyTrxReportRow> lineAggregator = new CustomLineAggregator<>();
            lineAggregator.setFieldExtractor(item -> new Object[]{
                            item.formattedTransactionDatetime,
                            item.maskedAcctNo,
                            item.tickerNameKr,
                            item.type,
                            item.formattedQuantity,
                            item.formattedPrice,
                            item.formattedAmount,
                            item.currency
                    }
            );
            return lineAggregator;
        }
    }

    @Setter
    static class CustomLineAggregator<T> implements LineAggregator<T> {

        private FieldExtractor<T> fieldExtractor;

        @Override
        public String aggregate(T item) {
            Object[] fields = fieldExtractor.extract(item);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < fields.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                String field = fields[i].toString();
                if (field.contains(",")) {
                    sb.append("\"").append(field).append("\"");
                } else {
                    sb.append(field);
                }
            }

            return sb.toString();
        }
    }

}
