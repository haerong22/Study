package org.example.trxbatch.service.email.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkReserveMonthlyTrxReportRequestTemplateContent {

    @JsonProperty("tableRows")
    private List<MonthlyTrxReportRow> monthlyTrxReportRows;

    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MonthlyTrxReportRow {
        @JsonProperty("col1")
        private String acctNo;
        @JsonProperty("col2")
        private String tickerNameKr;
        @JsonProperty("col3")
        private String transactionAt;
        @JsonProperty("col4")
        private String type;
        @JsonProperty("col5")
        private String price;
        @JsonProperty("col6")
        private String quantity;
        @JsonProperty("col7")
        private String amount;
        @JsonProperty("col8")
        private String currency;
    }


}