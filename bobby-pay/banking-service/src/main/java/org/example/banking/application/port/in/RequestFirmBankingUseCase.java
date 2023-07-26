package org.example.banking.application.port.in;

import org.example.banking.domain.FirmBankingRequest;

public interface RequestFirmBankingUseCase {

    FirmBankingRequest requestFirmBanking(RequestFirmBankingCommand command);
}
