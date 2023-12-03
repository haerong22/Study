package org.example.money.application.port.out;

import org.example.money.domain.MemberMoney;

import java.util.List;

public interface GetMemberMoneyListPort {

    List<MemberMoney> getMemberMoney(List<Long> membershipIds);
}
