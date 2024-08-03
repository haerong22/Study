package org.example.elsenrollment.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.elsenrollment.domain.entity.Payment;
import org.example.elsenrollment.domain.entity.PaymentType;
import org.example.elsenrollment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment createPayment(Long userId, PaymentType type, BigDecimal amount, String paymentMethod) {
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setPaymentType(type);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }

    @Transactional
    public Payment updatePaymentMethod(Long paymentId, String newPaymentMethod) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment != null) {
            payment.setPaymentMethod(newPaymentMethod);
            paymentRepository.save(payment);
        }
        return payment;
    }

    public List<Payment> getUserPayments(long userId) {
        return paymentRepository.findByUserId(userId);
    }
}