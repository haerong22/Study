package org.example.remittance.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.common.PersistenceAdapter;
import org.example.remittance.application.port.in.FindRemittanceCommand;
import org.example.remittance.application.port.in.RequestRemittanceCommand;
import org.example.remittance.application.port.out.FindRemittancePort;
import org.example.remittance.application.port.out.RequestRemittancePort;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class RemittanceRequestPersistenceAdapter implements RequestRemittancePort, FindRemittancePort {

    private final SpringDataRemittanceRequestRepository remittanceRequestRepository;

    @Override
    public RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command) {
        return remittanceRequestRepository.save(RemittanceRequestJpaEntity.builder()
                .fromMembershipId(command.getFromMembershipId())
                .toMembershipId(command.getToMembershipId())
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .amount(command.getAmount())
                .remittanceType(command.getRemittanceType())
                .build());
    }

    @Override
    public boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity) {
        remittanceRequestRepository.save(entity);
        return true;
    }

    @Override
    public List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command) {
        return null;
    }
}