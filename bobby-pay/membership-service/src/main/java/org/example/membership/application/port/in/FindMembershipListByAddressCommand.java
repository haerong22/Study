package org.example.membership.application.port.in;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public
class FindMembershipListByAddressCommand extends SelfValidating<FindMembershipListByAddressCommand> {

    private final String addressName;
}