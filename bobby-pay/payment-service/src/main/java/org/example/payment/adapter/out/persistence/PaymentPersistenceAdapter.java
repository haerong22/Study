package org.example.payment.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.common.PersistenceAdapter;
import org.example.payment.application.port.out.CreatePaymentPort;
import org.example.payment.application.port.out.GetPaymentCompletePort;
import org.example.payment.domain.Payment;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements CreatePaymentPort, GetPaymentCompletePort {

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

    @Override
    public List<Payment> getPaymentComplete() {
        return paymentRepository.findAllByPaymentStatus(0).stream()
                .map(mapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}
