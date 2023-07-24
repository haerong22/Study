package org.example.banking.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterBankAccountCommand extends SelfValidating<RegisterBankAccountCommand> {

    @NotEmpty
    private final String membershipId;

    @NotEmpty
    private final String bankName;

    @NotEmpty
    private final String bankAccountNumber;

    @AssertTrue
    private final boolean isValid;

    public RegisterBankAccountCommand(String membershipId, String bankName, String bankAccountNumber, boolean isValid) {
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.isValid = isValid;

        this.validateSelf();
    }
}
