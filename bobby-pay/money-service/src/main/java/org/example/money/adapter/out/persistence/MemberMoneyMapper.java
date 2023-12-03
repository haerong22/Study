package org.example.money.adapter.out.persistence;

import org.example.money.domain.MemberMoney;
import org.springframework.stereotype.Component;

@Component
public class MemberMoneyMapper {

    public MemberMoney mapToDomainEntity(MemberMoneyJpaEntity memberMoneyJpaEntity) {
        return MemberMoney.generateMemberMoney(
                new MemberMoney.MemberMoneyId(memberMoneyJpaEntity.getMemberMoneyId() + ""),
                new MemberMoney.MembershipId(memberMoneyJpaEntity.getMemberMoneyId() +""),
                new MemberMoney.Balance(memberMoneyJpaEntity.getBalance())
        );
    }
}
