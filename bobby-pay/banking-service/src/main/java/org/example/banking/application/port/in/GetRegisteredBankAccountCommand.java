package org.example.banking.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class GetRegisteredBankAccountCommand extends SelfValidating<GetRegisteredBankAccountCommand> {

    @NotNull
    private final String membershipId;

    public GetRegisteredBankAccountCommand(String membershipId) {
        this.membershipId = membershipId;

        this.validateSelf();
    }
}
