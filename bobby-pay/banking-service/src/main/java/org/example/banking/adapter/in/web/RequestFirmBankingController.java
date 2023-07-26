package org.example.banking.adapter.in.web;


import lombok.RequiredArgsConstructor;
import org.example.banking.application.port.in.RequestFirmBankingCommand;
import org.example.banking.application.port.in.RequestFirmBankingUseCase;
import org.example.banking.domain.FirmBankingRequest;
import org.example.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmBankingController {

    private final RequestFirmBankingUseCase requestFirmBankingUseCase;

    @PostMapping("/banking/firmbanking/request")
    FirmBankingRequest firmBankingRequest(@RequestBody RequestFirmBankingRequest request) {

        RequestFirmBankingCommand command = RequestFirmBankingCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        FirmBankingRequest firmBankingRequest = requestFirmBankingUseCase.requestFirmBanking(command);

        if (firmBankingRequest == null) {

            // TODO Error Handling
            return null;
        }

        return firmBankingRequest;
    }
}
