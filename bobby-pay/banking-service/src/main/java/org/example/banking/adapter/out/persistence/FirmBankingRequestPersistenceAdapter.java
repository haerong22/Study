package org.example.banking.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.banking.application.port.out.RequestFirmBankingPort;
import org.example.banking.domain.FirmBankingRequest;
import org.example.common.PersistenceAdapter;

import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmBankingRequestPersistenceAdapter implements RequestFirmBankingPort {

    private final SpringDataFirmBankingRequestRepository firmBankingRequestRepository;

    @Override
    public FirmBankingRequestJpaEntity createRequestFirmBanking(FirmBankingRequest.FromBankName fromBankName, FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber, FirmBankingRequest.ToBankName toBankName, FirmBankingRequest.ToBankAccountNumber toBankAccountNumber, FirmBankingRequest.MoneyAmount moneyAmount, FirmBankingRequest.FirmBankingStatus firmBankingStatus, FirmBankingRequest.FirmBankingAggregateIdentifier aggregateIdentifier) {
        return firmBankingRequestRepository.save(
                new FirmBankingRequestJpaEntity(
                        fromBankName.getFromBankName(),
                        fromBankAccountNumber.getFromBankAccountNumber(),
                        toBankName.getToBankName(),
                        toBankAccountNumber.getToBankAccountNumber(),
                        moneyAmount.getMoneyAmount(),
                        firmBankingStatus.getFirmBankingStatus(),
                        UUID.randomUUID(),
                        aggregateIdentifier.getAggregateIdentifier()
                )
        );
    }

    @Override
    public FirmBankingRequestJpaEntity modifyRequestFirmBanking(FirmBankingRequestJpaEntity entity) {
        return firmBankingRequestRepository.save(entity);
    }

    @Override
    public FirmBankingRequestJpaEntity getFirmBankingRequest(FirmBankingRequest.FirmBankingAggregateIdentifier firmBankingAggregateIdentifier) {
        return firmBankingRequestRepository.findByAggregateIdentifier(firmBankingAggregateIdentifier.getAggregateIdentifier());
    }
}
