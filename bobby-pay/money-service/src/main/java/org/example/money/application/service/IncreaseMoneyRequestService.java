package org.example.money.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.common.tasks.RechargingMoneyTask;
import org.example.common.tasks.SubTask;
import org.example.money.adapter.out.persistence.MemberMoneyJpaEntity;
import org.example.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import org.example.money.adapter.out.persistence.MoneyChangingRequestMapper;
import org.example.money.application.port.in.IncreaseMoneyRequestCommand;
import org.example.money.application.port.in.IncreaseMoneyRequestUseCase;
import org.example.money.application.port.out.GetMembershipPort;
import org.example.money.application.port.out.IncreaseMoneyPort;
import org.example.money.application.port.out.SendRechargingMoneyTaskPort;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@UseCase
@Transactional
@RequiredArgsConstructor
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final IncreaseMoneyPort increaseMoneyPort;
    private final GetMembershipPort getMembershipPort;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final MoneyChangingRequestMapper mapper;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        getMembershipPort.getMembership(command.getTargetMembershipId());

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

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

        // subtask, task
        SubTask validMemberTask = SubTask.builder()
                .subTaskName("validMemberTask : 멤버십 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName("validBankingAccountTask : 뱅킹 계좌 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();

        ArrayList<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validBankingAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("Increase Money Task / 머니 충전 Task")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipID(command.getTargetMembershipId())
                .toBankName("kb")
                .build();

        // kafka produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);

        // wait

        // task-consumer

        // task result consume

        // business logic

        return null;
    }
}
