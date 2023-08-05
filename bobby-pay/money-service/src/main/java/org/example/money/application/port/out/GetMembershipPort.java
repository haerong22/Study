package org.example.money.application.port.out;

import org.example.money.adapter.out.service.MembershipStatus;

public interface GetMembershipPort {

    MembershipStatus getMembership(String membershipId);
}
