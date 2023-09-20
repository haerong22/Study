package org.example.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFirmBankingRequest {

    private String firmBankingRequestAggregateIdentifier;
    private int status;
}
