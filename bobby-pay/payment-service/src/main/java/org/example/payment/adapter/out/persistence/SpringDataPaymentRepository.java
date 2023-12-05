package org.example.payment.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataPaymentRepository extends JpaRepository<PaymentJpaEntity, Long> {

    List<PaymentJpaEntity> findAllByPaymentStatus(int paymentStatus);
}
