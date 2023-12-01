package org.example.money.adapter.axon.aggregate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import org.example.money.adapter.axon.command.MemberMoneyCreatedCommand;
import org.example.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import org.example.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import org.example.money.adapter.axon.event.MemberMoneyCreatedEvent;
import org.example.money.adapter.axon.event.RechargingRequestCreatedEvent;
import org.example.money.application.port.out.GetRegisteredBankAccountPort;
import org.example.money.application.port.out.RegisteredBankAccountAggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate()
@Data
public class MemberMoneyAggregate {

    @AggregateIdentifier
    private String id;
    private Long membershipId;
    private int balance;

    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
        log.info("MemberMoneyCreatedCommand handler");

        apply(new MemberMoneyCreatedEvent(command.getMembershipId()));
    }

    @CommandHandler
    public String handle(IncreaseMemberMoneyCommand command) {
        log.info("IncreaseMemberMoneyCommand handler");
        id = command.getAggregateIdentifier();

        apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
        return id;
    }

    @CommandHandler
    public void handle(RechargingMoneyRequestCreateCommand command, GetRegisteredBankAccountPort getRegisteredBankAccountPort) {
        log.info("RechargingMoneyRequestCreateCommand Handler");
        id = command.getAggregateIdentifier();

        RegisteredBankAccountAggregateIdentifier registeredBankAccountAggregateIdentifier
                = getRegisteredBankAccountPort.getRegisteredBankAccount(command.getMembershipId());

        // saga start
        apply(new RechargingRequestCreatedEvent(
                command.getRechargingRequestId(),
                command.getMembershipId(),
                command.getAmount(),
                registeredBankAccountAggregateIdentifier.getAggregateIdentifier(),
                registeredBankAccountAggregateIdentifier.getBankName(),
                registeredBankAccountAggregateIdentifier.getBankAccountNumber()
        ));
    }

    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) {
        log.info("MemberMoneyCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getMembershipId());
        balance = 0;
    }

    @EventSourcingHandler
    public void on(IncreaseMemberMoneyEvent event) {
        log.info("MemberMoneyCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getTargetMembershipId());
        balance = event.getAmount();
    }


    public MemberMoneyAggregate() {
    }
}
