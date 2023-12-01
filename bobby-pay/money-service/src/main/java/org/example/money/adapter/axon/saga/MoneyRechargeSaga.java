package org.example.money.adapter.axon.saga;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.example.common.event.*;
import org.example.money.adapter.axon.event.RechargingRequestCreatedEvent;
import org.example.money.adapter.out.persistence.MemberMoneyJpaEntity;
import org.example.money.application.port.out.IncreaseMoneyPort;
import org.example.money.domain.MemberMoney;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Slf4j
@Saga
@NoArgsConstructor
public class MoneyRechargeSaga {

    @NonNull
    private transient CommandGateway commandGateway;

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "rechargingRequestId")
    public void handle(RechargingRequestCreatedEvent event) {
        // 충전 요청 시작
        log.info("RechargingRequestCreatedEvent Start saga");

        String checkRegisteredBankAccountId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("checkRegisteredBankAccountId", checkRegisteredBankAccountId);

        // 뱅킹의 계좌 등록 여부 확인 (RegisteredBankAccount)
        // -> CheckRegisteredBankAccountCommand
        commandGateway.send(
                new CheckRegisteredBankAccountCommand(
                        event.getRegisteredBankAccountAggregateIdentifier(),
                        event.getRechargingRequestId(),
                        event.getMembershipId(),
                        checkRegisteredBankAccountId,
                        event.getBankName(),
                        event.getBankAccountNumber(),
                        event.getAmount()
                )
        ).whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                        log.info("CheckRegisteredBankAccountCommand Failed");
                    } else {
                        log.info("CheckRegisteredBankAccountCommand Success");
                    }
                }
        );
    }

    @SagaEventHandler(associationProperty = "checkRegisteredBankAccountId")
    public void handle(CheckRegisteredBankAccountEvent event) {
        log.info("CheckRegisteredBankAccountEvent Saga: {}", event.toString());

        if (event.isChecked()) {
            log.info("CheckRegisteredBankAccountEvent Success");
        } else {
            log.info("CheckRegisteredBankAccountEvent Failed");
        }

        String requestFirmBankingId = UUID.randomUUID().toString();

        SagaLifecycle.associateWith("requestFirmBankingId", requestFirmBankingId);

        // 송금요청 고객계좌 -> 법인계좌
        commandGateway.send(new RequestFirmBankingCommand(
                requestFirmBankingId,
                event.getFirmBankingRequestAggregateIdentifier(),
                event.getRechargingRequestId(),
                event.getMembershipId(),
                event.getFromBankName(),
                event.getFromBankAccountNumber(),
                "bobby",
                "123456789",
                event.getAmount()
        )).whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                        log.info("RequestFirmBankingCommand Failed");
                    } else {
                        log.info("RequestFirmBankingCommand Success");
                    }
                }
        );
    }

    @SagaEventHandler(associationProperty = "requestFirmBankingId")
    public void handle(RequestFirmBankingFinishedEvent event, IncreaseMoneyPort increaseMoneyPort) {
        log.info("RequestFirmBankingFinishedEvent Saga: {}", event.toString());

        if (event.getStatus() == 0) {
            log.info("CheckRegisteredBankAccountEvent Success");
        } else {
            log.info("CheckRegisteredBankAccountEvent Failed");
        }

        MemberMoneyJpaEntity resultEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(event.getMembershipId()),
                event.getAmount()
        );

        if (resultEntity == null) {
            // 실패 시 롤백 이벤
            String rollbackFirmBankingId = UUID.randomUUID().toString();

            SagaLifecycle.associateWith("rollbackFirmBankingId", rollbackFirmBankingId);

            commandGateway.send(new RollbackFirmBankingRequestCommand(
                    rollbackFirmBankingId,
                    event.getRequestFirmBankingAggregateIdentifier(),
                    event.getRechargingRequestId(),
                    event.getMembershipId(),
                    event.getToBankName(),
                    event.getToBankAccountNumber(),
                    event.getAmount()
            )).whenComplete(
                    (result, throwable) -> {
                        if (throwable != null) {
                            throwable.printStackTrace();
                            log.info("RollbackFirmBankingRequestCommand Failed");
                        } else {
                            log.info("RollbackFirmBankingRequestCommand Success");
                            SagaLifecycle.end();
                        }
                    }
            );
        } else {
            // 성공 시 saga 종료
            SagaLifecycle.end();
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "rollbackFirmBankingId")
    public void handle(RollbackFirmBankingFinishedEvent event) {
        log.info("RollbackFirmBankingFinishedEvent saga: {}", event.toString());
    }

}
