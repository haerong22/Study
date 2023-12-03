package org.example.membership.application.port.in;

import org.example.membership.domain.Membership;

import java.util.List;

public interface FindMembershipUseCase {

    Membership findMembership(FindMembershipCommand command);
    List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command);
}