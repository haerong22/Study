package org.example.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckRegisteredBankAccountEvent {

    private String rechargingRequestId;

    private String checkRegisteredBankAccountId;

    private String membershipId;

    private boolean isChecked;

    private int amount;

    private String firmBankingRequestAggregateIdentifier;

    private String fromBankName;

    private String fromBankAccountNumber;

}
