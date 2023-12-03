package org.example.money.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.UseCase;
import org.example.money.application.port.in.FindMemberMoneyListByMembershipIdsRequestCommand;
import org.example.money.application.port.in.FindMemberMoneyListByMembershipIdsRequestUseCase;
import org.example.money.application.port.out.GetMemberMoneyListPort;
import org.example.money.domain.MemberMoney;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class FindMemberMoneyListService implements FindMemberMoneyListByMembershipIdsRequestUseCase {

    private final GetMemberMoneyListPort getMemberMoneyListPort;

    @Override
    public List<MemberMoney> findMemberMoneyListByMembershipIds(FindMemberMoneyListByMembershipIdsRequestCommand command) {
        return getMemberMoneyListPort.getMemberMoney(command.getMembershipIds());
    }
}
