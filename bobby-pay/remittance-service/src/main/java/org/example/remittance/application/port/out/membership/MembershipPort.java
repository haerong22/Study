package org.example.remittance.application.port.out.membership;

import org.example.remittance.adapter.out.service.membership.MembershipStatus;

public interface MembershipPort {

    MembershipStatus getMembershipStatus(String membershipId);
}