package org.example.banking.application.port.out;

import org.example.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import org.example.banking.adapter.out.external.bank.FirmBankingResult;

public interface RequestExternalFirmBankingPort {

    FirmBankingResult requestExternalFirmBanking(ExternalFirmBankingRequest request);
}
