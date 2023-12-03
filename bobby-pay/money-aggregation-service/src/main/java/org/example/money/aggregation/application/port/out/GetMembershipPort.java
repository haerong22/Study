package org.example.money.aggregation.application.port.out;

import java.util.List;

public interface GetMembershipPort {

    List<Long> getMembershipByAddress(String address);
}
