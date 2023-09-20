package org.example.banking.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateFirmBankingCommand extends SelfValidating<UpdateFirmBankingCommand> {

    @NotEmpty
    private final String firmBankingAggregateIdentifier;

    private final int firmBankingStatus;

    public UpdateFirmBankingCommand(String firmBankingAggregateIdentifier, int firmBankingStatus) {
        this.firmBankingAggregateIdentifier = firmBankingAggregateIdentifier;
        this.firmBankingStatus = firmBankingStatus;
        this.validateSelf();
    }
}
