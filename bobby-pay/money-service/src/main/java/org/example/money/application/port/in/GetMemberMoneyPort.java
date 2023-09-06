package org.example.money.application.port.in;


import org.example.money.adapter.out.persistence.MemberMoneyJpaEntity;
import org.example.money.domain.MemberMoney;

public interface GetMemberMoneyPort {
    MemberMoneyJpaEntity getMemberMoney(
            MemberMoney.MembershipId memberId
    );
}