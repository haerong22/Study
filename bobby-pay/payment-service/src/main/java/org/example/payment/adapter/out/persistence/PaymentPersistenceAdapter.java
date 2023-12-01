package org.example.payment.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.common.PersistenceAdapter;
import org.example.payment.application.port.out.CreatePaymentPort;
import org.example.payment.domain.Payment;

@PersistenceAdapter
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements CreatePaymentPort {

    private final SpringDataPaymentRepository paymentRepository;

    private final PaymentRequestMapper mapper;

    @Override
    public Payment createPayment(
            String requestMembershipId,
            int requestPrice,
            String franchiseId,
            String franchiseFeeRate
    ) {

        PaymentJpaEntity jpaEntity = paymentRepository.save(
                new PaymentJpaEntity(
                        requestMembershipId,
                        requestPrice,
                        franchiseId,
                        franchiseFeeRate,
                        0,
                        null
                )
        );
        return mapper.mapToDomainEntity(jpaEntity);
    }
}
