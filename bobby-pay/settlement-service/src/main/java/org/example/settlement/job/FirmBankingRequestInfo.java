package org.example.settlement.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirmBankingRequestInfo {

    private String bankName;
    private String bankAccountNumber;
    private int moneyAmount;

    public FirmBankingRequestInfo(String bankName, String bankAccountNumber) {
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
    }
}