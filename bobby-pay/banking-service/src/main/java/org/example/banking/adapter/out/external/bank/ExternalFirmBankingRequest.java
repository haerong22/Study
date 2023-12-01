package org.example.banking.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExternalFirmBankingRequest {

    private String fromBankName;
    private String fromBankAccountName;

    private String toBankName;
    private String toBankAccountName;

    private int amount;

}
