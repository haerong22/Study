package org.example.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFirmBankingFinishedEvent {

    private String requestFirmBankingId;
    private String rechargingRequestId;
    private String membershipId;
    private String toBankName;
    private String toBankAccountNumber;
    private int amount;
    private int status;
    private String requestFirmBankingAggregateIdentifier;
}
