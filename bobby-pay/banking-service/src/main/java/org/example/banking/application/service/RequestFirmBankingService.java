package org.example.banking.application.service;

import lombok.RequiredArgsConstructor;
import org.example.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import org.example.banking.adapter.out.external.bank.FirmBankingResult;
import org.example.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;
import org.example.banking.adapter.out.persistence.FirmBankingRequestMapper;
import org.example.banking.application.port.in.RequestFirmBankingCommand;
import org.example.banking.application.port.in.RequestFirmBankingUseCase;
import org.example.banking.application.port.out.RequestExternalFirmBankingPort;
import org.example.banking.application.port.out.RequestFirmBankingPort;
import org.example.banking.domain.FirmBankingRequest;
import org.example.common.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RequestFirmBankingService implements RequestFirmBankingUseCase {

    private final RequestFirmBankingPort requestFirmBankingPort;
    private final FirmBankingRequestMapper mapper;
    private final RequestExternalFirmBankingPort requestExternalFirmBankingPort;

    @Override
    public FirmBankingRequest requestFirmBanking(RequestFirmBankingCommand command) {

        FirmBankingRequestJpaEntity requestedEntity = requestFirmBankingPort.createRequestFirmBanking(
                new FirmBankingRequest.FromBankName(command.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(command.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmBankingRequest.FirmBankingStatus(0)
        );

        FirmBankingResult result = requestExternalFirmBankingPort.requestExternalFirmBanking(new ExternalFirmBankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber()
        ));

        UUID randomUUID = UUID.randomUUID();
        requestedEntity.setUuid(randomUUID.toString());

        if (result.getResultCode() == 0) {
            requestedEntity.setFirmBankingStatus(1);
        } else {
            requestedEntity.setFirmBankingStatus(2);
        }

        return mapper.mapToDomainEntity(requestFirmBankingPort.modifyRequestFirmBanking(requestedEntity), randomUUID);
    }
}
