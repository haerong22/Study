package org.example.money.application.port.in;

import org.example.money.domain.MemberMoney;

import java.util.List;

public interface FindMemberMoneyListByMembershipIdsRequestUseCase {

    List<MemberMoney> findMemberMoneyListByMembershipIds(FindMemberMoneyListByMembershipIdsRequestCommand command);
}
