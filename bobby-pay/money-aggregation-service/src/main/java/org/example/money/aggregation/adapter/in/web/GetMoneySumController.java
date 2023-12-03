package org.example.money.aggregation.adapter.in.web;


import lombok.RequiredArgsConstructor;
import org.example.common.WebAdapter;
import org.example.money.aggregation.application.port.in.GetMoneySumByAddressCommand;
import org.example.money.aggregation.application.port.in.GetMoneySumByAddressUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetMoneySumController {

    private final GetMoneySumByAddressUseCase getMoneySumByAddressUseCase;

    @PostMapping("/money/aggregation/total")
    int getMoneySumByAddress(@RequestBody GetMoneySumByAddressRequest request) {
        return getMoneySumByAddressUseCase.getMoneySumByAddress(
                GetMoneySumByAddressCommand.builder()
                        .address(request.getAddress())
                        .build()
        );
    }
}
