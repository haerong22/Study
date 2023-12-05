package org.example.payment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.payment.application.port.in.*;
import org.example.payment.application.port.out.*;
import org.example.payment.domain.Payment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class PaymentService implements
        RequestPaymentUseCase,
        GetPaymentCompleteUseCase,
        PaymentSettlementUseCase
{

    private final CreatePaymentPort createPaymentPort;
    private final GetMembershipPort getMembershipPort;
    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;
    private final GetPaymentCompletePort getPaymentCompletePort;
    private final PaymentSettlementPort paymentSettlementPort;

    @Override
    public Payment requestPayment(RequestPaymentCommand command) {
        return createPaymentPort.createPayment(
                command.getRequestMembershipId(),
                command.getRequestPrice(),
                command.getFranchiseId(),
                command.getFranchiseFeeRate()
        );
    }

    @Override
    public List<Payment> getPaymentComplete() {
        return getPaymentCompletePort.getPaymentComplete();
    }

    @Override
    public void paymentSettlement(PaymentSettlementCommand command) {
        paymentSettlementPort.changePaymentStatus(command.getPaymentId(), 2);
    }
}
