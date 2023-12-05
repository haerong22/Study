package org.example.payment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.payment.application.port.in.GetPaymentCompleteUseCase;
import org.example.payment.application.port.in.RequestPaymentCommand;
import org.example.payment.application.port.in.RequestPaymentUseCase;
import org.example.payment.application.port.out.CreatePaymentPort;
import org.example.payment.application.port.out.GetMembershipPort;
import org.example.payment.application.port.out.GetPaymentCompletePort;
import org.example.payment.application.port.out.GetRegisteredBankAccountPort;
import org.example.payment.domain.Payment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class PaymentService implements RequestPaymentUseCase, GetPaymentCompleteUseCase {

    private final CreatePaymentPort createPaymentPort;
    private final GetMembershipPort getMembershipPort;
    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;
    private final GetPaymentCompletePort getPaymentCompletePort;

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
}
