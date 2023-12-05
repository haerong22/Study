package org.example.payment.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.common.PersistenceAdapter;
import org.example.payment.application.port.out.CreatePaymentPort;
import org.example.payment.application.port.out.GetPaymentCompletePort;
import org.example.payment.application.port.out.PaymentSettlementPort;
import org.example.payment.domain.Payment;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@PersistenceAdapter
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements
        CreatePaymentPort,
        GetPaymentCompletePort,
        PaymentSettlementPort
{

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

    @Override
    public void changePaymentStatus(Long paymentId, int status) {
        PaymentJpaEntity entity = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 내역이 없습니다."));

        entity.setPaymentStatus(status);
    }
}
