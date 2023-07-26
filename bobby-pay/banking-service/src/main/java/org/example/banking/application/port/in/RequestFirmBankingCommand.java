package org.example.banking.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RequestFirmBankingCommand extends SelfValidating<RequestFirmBankingCommand> {

    @NotEmpty
    private final String fromBankName;

    @NotEmpty
    private final String fromBankAccountNumber;

    @NotEmpty
    private final String toBankName;

    @NotEmpty
    private final String toBankAccountNumber;

    private final int moneyAmount;

    public RequestFirmBankingCommand(String fromBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, int moneyAmount) {
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;

        this.validateSelf();
    }
}
