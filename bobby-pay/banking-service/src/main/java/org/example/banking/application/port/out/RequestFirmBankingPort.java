package org.example.banking.application.port.out;

import org.example.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;
import org.example.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import org.example.banking.domain.FirmBankingRequest;
import org.example.banking.domain.RegisteredBankAccount;

public interface RequestFirmBankingPort {

    FirmBankingRequestJpaEntity createRequestFirmBanking(
            FirmBankingRequest.FromBankName fromBankName,
            FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmBankingRequest.ToBankName toBankName,
            FirmBankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmBankingRequest.MoneyAmount moneyAmount,
            FirmBankingRequest.FirmBankingStatus firmBankingStatus
    );

    FirmBankingRequestJpaEntity modifyRequestFirmBanking(
            FirmBankingRequestJpaEntity entity
    );
}
