package org.example.payment.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.common.WebAdapter;
import org.example.payment.application.port.in.*;
import org.example.payment.domain.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestPaymentController {

    private final RequestPaymentUseCase requestPaymentUseCase;
    private final GetPaymentCompleteUseCase getPaymentCompleteUseCase;
    private final PaymentSettlementUseCase paymentSettlementUseCase;

    @PostMapping("/payment/request")
    Payment requestPayment(@RequestBody PaymentRequest request) {

        return requestPaymentUseCase.requestPayment(
                new RequestPaymentCommand(
                        request.getRequestMembershipId(),
                        request.getRequestPrice(),
                        request.getFranchiseId(),
                        request.getFranchiseFeeRate()
                )
        );
    }

    @GetMapping("/payment/complete")
    List<Payment> getPaymentComplete() {
        return getPaymentCompleteUseCase.getPaymentComplete();
    }

    @PostMapping("/payment/settlement")
    void settlement(@RequestBody PaymentSettlementRequest request) {

        paymentSettlementUseCase.paymentSettlement(
                new PaymentSettlementCommand(request.getPaymentId())
        );
    }
}
