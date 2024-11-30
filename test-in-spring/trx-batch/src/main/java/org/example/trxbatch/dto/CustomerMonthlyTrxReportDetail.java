package org.example.trxbatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.trxbatch.dto.enums.TransactionType;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerMonthlyTrxReportDetail {
    private String acctNo;
    private String tickerNameKr;
    private LocalDateTime transactionAt;
    private TransactionType type;
    private long price;
    private long quantity;
    private BigInteger amount;
    private String currency;
}
