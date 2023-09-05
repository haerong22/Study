package org.example.money.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateMemberMoneyCommand extends SelfValidating<CreateMemberMoneyCommand> {

    @NotEmpty
    private final String membershipId;

    public CreateMemberMoneyCommand(String targetMembershipId) {
        this.membershipId = targetMembershipId;
        this.validateSelf();
    }
}
