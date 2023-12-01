package org.example.banking.adapter.axon.aggregate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.banking.adapter.axon.command.CreateFirmBankingRequestCommand;
import org.example.banking.adapter.axon.command.UpdateFirmBankingRequestCommand;
import org.example.banking.adapter.axon.event.FirmBankingRequestCreatedEvent;
import org.example.banking.adapter.axon.event.FirmBankingRequestUpdatedEvent;
import org.example.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import org.example.banking.adapter.out.external.bank.FirmBankingResult;
import org.example.banking.application.port.out.RequestExternalFirmBankingPort;
import org.example.banking.application.port.out.RequestFirmBankingPort;
import org.example.banking.domain.FirmBankingRequest;
import org.example.common.event.RequestFirmBankingCommand;
import org.example.common.event.RequestFirmBankingFinishedEvent;
import org.example.common.event.RollbackFirmBankingFinishedEvent;
import org.example.common.event.RollbackFirmBankingRequestCommand;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate()
@Data
public class FirmBankingRequestAggregate {

    @AggregateIdentifier
    private String id;

    private String fromBankName;
    private String fromBankAccountNumber;

    private String toBankName;
    private String toBankAccountNumber;

    private int moneyAmount;

    private int firmBankingStatus;

    @CommandHandler
    public FirmBankingRequestAggregate(CreateFirmBankingRequestCommand command) {
        log.info("CreateFirmBankingRequestCommand Handler");

        apply(new FirmBankingRequestCreatedEvent(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount()
        ));
    }

    @CommandHandler
    public String handle(UpdateFirmBankingRequestCommand command) {
        log.info("UpdateFirmBankingRequestCommand Handler");

        id = command.getAggregateIdentifier();
        apply(new FirmBankingRequestUpdatedEvent(command.getFirmBankingStatus()));

        return id;
    }

    @CommandHandler
    public FirmBankingRequestAggregate(RequestFirmBankingCommand command, RequestFirmBankingPort requestFirmBankingPort, RequestExternalFirmBankingPort externalFirmBankingPort) {
        log.info("RequestFirmBankingCommand Handler");

        id = command.getAggregateIdentifier();

        requestFirmBankingPort.createRequestFirmBanking(
                new FirmBankingRequest.FromBankName(command.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(command.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(command.getAmount()),
                new FirmBankingRequest.FirmBankingStatus(0),
                new FirmBankingRequest.FirmBankingAggregateIdentifier(id)
        );

        FirmBankingResult firmBankingResult = externalFirmBankingPort.requestExternalFirmBanking(new ExternalFirmBankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getAmount()
        ));

        int resultCode = firmBankingResult.getResultCode();

        apply(new RequestFirmBankingFinishedEvent(
                command.getRequestFirmBankingId(),
                command.getRechargeRequestId(),
                command.getMembershipId(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getAmount(),
                resultCode,
                id
        ));
    }

    @CommandHandler
    public FirmBankingRequestAggregate(RollbackFirmBankingRequestCommand command, RequestFirmBankingPort requestFirmBankingPort, RequestExternalFirmBankingPort externalFirmBankingPort) {
        log.info("RollbackFirmBankingRequestCommand Handler");

        id = UUID.randomUUID().toString();

        // rollback 법인계좌 -> 고객계좌
        requestFirmBankingPort.createRequestFirmBanking(
                new FirmBankingRequest.FromBankName("bobby"),
                new FirmBankingRequest.FromBankAccountNumber("123456789"),
                new FirmBankingRequest.ToBankName(command.getBankName()),
                new FirmBankingRequest.ToBankAccountNumber(command.getBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(command.getAmount()),
                new FirmBankingRequest.FirmBankingStatus(0),
                new FirmBankingRequest.FirmBankingAggregateIdentifier(id)
        );

        FirmBankingResult result = externalFirmBankingPort.requestExternalFirmBanking(
                new ExternalFirmBankingRequest(
                        "bobby",
                        "123456789",
                        command.getBankName(),
                        command.getBankAccountNumber(),
                        command.getAmount()
                )
        );

        int resultCode = result.getResultCode();

        apply(new RollbackFirmBankingFinishedEvent(
                command.getRollbackFirmBankingId(),
                command.getMembershipId(),
                id
        ));
    }

    @EventSourcingHandler
    public void on(FirmBankingRequestCreatedEvent event) {
        log.info("FirmBankingRequestCreatedEvent Sourcing Handler");

        id = UUID.randomUUID().toString();
        fromBankName = event.getFromBankName();
        fromBankAccountNumber = event.getFromBankAccountNumber();
        toBankName = event.getToBankName();
        toBankAccountNumber = event.getToBankAccountNumber();
    }

    @EventSourcingHandler
    public void on(FirmBankingRequestUpdatedEvent event) {
        log.info("FirmBankingRequestUpdatedEvent Sourcing Handler");

        firmBankingStatus = event.getFirmBankingStatus();
    }

    public FirmBankingRequestAggregate() {
    }
}
