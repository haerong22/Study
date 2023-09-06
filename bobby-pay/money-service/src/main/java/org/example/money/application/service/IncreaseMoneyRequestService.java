package org.example.money.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.common.CountDownLatchManager;
import org.example.common.UseCase;
import org.example.common.tasks.RechargingMoneyTask;
import org.example.common.tasks.SubTask;
import org.example.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import org.example.money.adapter.axon.command.MemberMoneyCreatedCommand;
import org.example.money.adapter.out.persistence.MemberMoneyJpaEntity;
import org.example.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import org.example.money.adapter.out.persistence.MoneyChangingRequestMapper;
import org.example.money.application.port.in.*;
import org.example.money.application.port.out.GetMembershipPort;
import org.example.money.application.port.out.IncreaseMoneyPort;
import org.example.money.application.port.out.SendRechargingMoneyTaskPort;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase, CreateMemberMoneyUseCase {

    private final IncreaseMoneyPort increaseMoneyPort;
    private final GetMembershipPort getMembershipPort;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final CountDownLatchManager countDownLatchManager;
    private final MoneyChangingRequestMapper mapper;
    private final CommandGateway commandGateway;
    private final CreateMemberMoneyPort createMemberMoneyPort;
    private final GetMemberMoneyPort getMemberMoneyPort;

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
        countDownLatchManager.addCountDownLatch(task.getTaskID());

        // wait
        try {
            countDownLatchManager.getCountDownLatch(task.getTaskID()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // task-consumer

        // task result consume
        String result = countDownLatchManager.getDataForKey(task.getTaskID());

        if (result.equals("success")) {
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


        } else {

        }
        return null;
    }

    @Override
    public void createMemberMoney(CreateMemberMoneyCommand command) {
        MemberMoneyCreatedCommand axonCommand = new MemberMoneyCreatedCommand(command.getMembershipId());
        commandGateway.send(axonCommand).whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.info("throwable =>", throwable);
                throw new RuntimeException(throwable);
            } else {
                log.info("result => {}", result);
                createMemberMoneyPort.createMemberMoney(
                        new MemberMoney.MembershipId(command.getMembershipId()),
                        new MemberMoney.MoneyAggregateIdentifier(result.toString())
                );
            }
        });
    }

    @Override
    public void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command) {
        MemberMoneyJpaEntity memberMoneyJpaEntity = getMemberMoneyPort.getMemberMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId())
        );

        String aggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();

        IncreaseMemberMoneyCommand axonCommand = IncreaseMemberMoneyCommand.builder()
                .aggregateIdentifier(aggregateIdentifier)
                .membershipId(command.getTargetMembershipId())
                .amount(command.getAmount())
                .build();

        commandGateway.send(axonCommand).whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.info("throwable =>", throwable);
                throw new RuntimeException(throwable);
            } else {
                log.info("result => {}", result);
                increaseMoneyPort.increaseMoney(
                        new MemberMoney.MembershipId(command.getTargetMembershipId()),
                        command.getAmount()
                );
            }
        });
    }
}
