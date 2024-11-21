package org.example.paymentservice.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TransactionServiceIntTest {
    @Autowired
    TransactionService transactionService;

    @Test
    @Transactional
    void 결제를_생성한다() {
        // given
        var request = new PaymentTransactionRequest(1L, "course-1", new BigDecimal(10));

        // when
        var response = transactionService.payment(request);

        // then
        assertNotNull(response);
        System.out.println(response);
    }

    @Test
    @Transactional
    void 충전_진행() {
        Long userId = 100L;
        String orderId = "orderId-1";

        // given
        var chargeTransactionRequest = new ChargeTransactionRequest(userId, orderId, BigDecimal.TEN);

        // when
        var response = transactionService.charge(chargeTransactionRequest);

        // then
        assertEquals(new BigDecimal("10010.00"), response.balance());
    }
}