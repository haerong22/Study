package org.example.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestFirmBankingCommand {

    private String requestFirmBankingId;

    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    private String rechargeRequestId;

    private String membershipId;

    private String fromBankName;

    private String fromBankAccountNumber;

    private String toBankName;

    private String toBankAccountNumber;

    private int amount;
}
