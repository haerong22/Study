package org.example.banking.application.port.out;

import org.example.banking.adapter.out.service.MembershipStatus;

public interface GetMembershipPort {

    MembershipStatus getMembership(String membershipId);
}
