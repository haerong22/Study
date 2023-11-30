package org.example.banking.adapter.axon.aggregate;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import org.example.banking.adapter.axon.event.CreateRegisteredBankAccountEvent;
import org.example.common.event.CheckRegisteredBankAccountCommand;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate()
@NoArgsConstructor
public class RegisteredBankAccountAggregate {

    @AggregateIdentifier
    private String id;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

    @CommandHandler
    public RegisteredBankAccountAggregate(CreateRegisteredBankAccountCommand command) {
        log.info("CreateRegisteredBankAccountCommand Command Handler");
        apply(new CreateRegisteredBankAccountEvent(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()));
    }

    @CommandHandler
    public void handle(CheckRegisteredBankAccountCommand command) {
        log.info("CheckRegisteredBankAccountCommand Handler");

    }

    @EventSourcingHandler
    public void on(CreateRegisteredBankAccountEvent event) {
        log.info("CreateRegisteredBankAccountEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = event.getMembershipId();
        bankName = event.getBankName();
        bankAccountNumber = event.getBankAccountNumber();
    }
}
