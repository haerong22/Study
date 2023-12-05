package org.example.settlement.port.out;

import org.example.settlement.adapter.out.service.Payment;

import java.util.List;

public interface PaymentPort {
    List<Payment> getPaymentComplete(); // membershipId = franchiseId 간주.

    // 타겟 계좌, 금액
    void settlement(Long paymentId);
}