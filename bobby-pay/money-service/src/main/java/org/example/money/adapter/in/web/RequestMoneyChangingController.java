package org.example.money.adapter.in.web;


import lombok.RequiredArgsConstructor;
import org.example.common.WebAdapter;
import org.example.money.application.port.in.*;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;
    private final CreateMemberMoneyUseCase createMemberMoneyUseCase;
    private final FindMemberMoneyListByMembershipIdsRequestUseCase findMemberMoneyListByMembershipIdsRequestUseCase;

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

    @PostMapping("/money/increase-async")
    MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);

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

    @PostMapping("/money/create-member-money")
    void createMemberMoney(@RequestBody CreateMemberMoneyRequest request) {
        CreateMemberMoneyCommand command = CreateMemberMoneyCommand.builder()
                .membershipId(request.getMembershipId())
                .build();

        createMemberMoneyUseCase.createMemberMoney(command);
    }

    @PostMapping(path = "/money/increase-eda")
    void increaseMoneyChangingRequestByEvent(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        increaseMoneyRequestUseCase.increaseMoneyRequestByEvent(command);
    }

    @PostMapping(path = "/money/decrease-eda")
    void decreaseMoneyChangingRequestByEvent(@RequestBody DecreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount() * -1)
                .build();

        increaseMoneyRequestUseCase.increaseMoneyRequestByEvent(command);
    }

    @PostMapping(path = "/money/member-money")
    List<MemberMoney> findMemberMoneyListByMembershipIdsRequest(@RequestBody FindMemberMoneyListByMembershipIdsRequest request) {
        FindMemberMoneyListByMembershipIdsRequestCommand command = FindMemberMoneyListByMembershipIdsRequestCommand.builder()
                .membershipIds(request.getMembershipId())
                .build();

        return findMemberMoneyListByMembershipIdsRequestUseCase.findMemberMoneyListByMembershipIds(command);
    }
}
