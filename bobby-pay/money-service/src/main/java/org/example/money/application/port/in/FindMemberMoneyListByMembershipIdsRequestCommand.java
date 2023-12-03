package org.example.money.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindMemberMoneyListByMembershipIdsRequestCommand extends SelfValidating<FindMemberMoneyListByMembershipIdsRequestCommand> {

    @NotNull
    private final List<Long> membershipIds;

    public FindMemberMoneyListByMembershipIdsRequestCommand(List<Long> membershipIds) {
        this.membershipIds = membershipIds;

        this.validateSelf();
    }
}
