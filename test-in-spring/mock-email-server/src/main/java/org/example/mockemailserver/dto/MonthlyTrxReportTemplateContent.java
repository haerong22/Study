package org.example.mockemailserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MonthlyTrxReportTemplateContent {
    private List<MonthlyTrxReportRow> monthlyTrxReportRows;

    @Data
    @NoArgsConstructor
    public static class MonthlyTrxReportRow {
        private String acctNo;
        private String tickerNameKr;
        private LocalDateTime transactionedAt;
        private String type;
        private int price;
        private int quantity;
        private BigInteger amount;
        private String currency;
    }
}