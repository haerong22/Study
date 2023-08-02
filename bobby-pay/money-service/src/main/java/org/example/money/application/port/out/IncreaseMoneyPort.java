package org.example.money.application.port.out;

import org.example.money.adapter.out.persistence.MemberMoneyJpaEntity;
import org.example.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyPort {

    MoneyChangingRequestJpaEntity createMoneyChangingRequest(
            MoneyChangingRequest.TargetMembershipId targetMembershipId,
            MoneyChangingRequest.MoneyChangingType moneyChangingType,
            MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus,
            MoneyChangingRequest.Uuid uuid

    );

    MemberMoneyJpaEntity increaseMoney(
            MemberMoney.MembershipId membershipId,
            int increaseMoneyAmount
    );
}
