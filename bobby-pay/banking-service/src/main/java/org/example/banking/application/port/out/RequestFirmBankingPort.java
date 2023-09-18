package org.example.banking.application.port.out;

import org.example.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;
import org.example.banking.domain.FirmBankingRequest;

public interface RequestFirmBankingPort {

    FirmBankingRequestJpaEntity createRequestFirmBanking(
            FirmBankingRequest.FromBankName fromBankName,
            FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmBankingRequest.ToBankName toBankName,
            FirmBankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmBankingRequest.MoneyAmount moneyAmount,
            FirmBankingRequest.FirmBankingStatus firmBankingStatus,
            FirmBankingRequest.FirmBankingAggregateIdentifier firmBankingAggregateIdentifier
    );

    FirmBankingRequestJpaEntity modifyRequestFirmBanking(
            FirmBankingRequestJpaEntity entity
    );
}
