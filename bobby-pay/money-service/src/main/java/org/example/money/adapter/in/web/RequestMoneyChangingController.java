package org.example.money.adapter.in.web;


import lombok.RequiredArgsConstructor;
import org.example.common.WebAdapter;
import org.example.money.application.port.in.IncreaseMoneyRequestCommand;
import org.example.money.application.port.in.IncreaseMoneyRequestUseCase;
import org.example.money.domain.MoneyChangingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;

    @PostMapping("/money/increase")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

        if (moneyChangingRequest == null) {

            // TODO Error Handling
            return null;
        }

        return new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChargingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount()
        );
    }
}