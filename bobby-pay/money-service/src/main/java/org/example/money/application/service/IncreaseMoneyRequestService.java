package org.example.money.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.money.adapter.out.persistence.MemberMoneyJpaEntity;
import org.example.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import org.example.money.adapter.out.persistence.MoneyChangingRequestMapper;
import org.example.money.application.port.in.IncreaseMoneyRequestCommand;
import org.example.money.application.port.in.IncreaseMoneyRequestUseCase;
import org.example.money.application.port.out.IncreaseMoneyPort;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@Transactional
@RequiredArgsConstructor
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper mapper;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        MemberMoneyJpaEntity memberMoneyEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),
                command.getAmount()
        );

        if (memberMoneyEntity != null) {

            MoneyChangingRequestJpaEntity entity = increaseMoneyPort.createMoneyChangingRequest(
                    new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                    new MoneyChangingRequest.MoneyChangingType(0),
                    new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                    new MoneyChangingRequest.MoneyChangingStatus(1),
                    new MoneyChangingRequest.Uuid(UUID.randomUUID())
            );
            return mapper.mapToDomainEntity(entity);

        }

        return null;
    }
}
