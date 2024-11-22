package org.example.paymentservice.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Test
    void 충전을_동시에_실행한다() throws InterruptedException {
        Long userId = 100L;
        String orderId = "orderId-2";
        ChargeTransactionRequest chargeTransactionRequest = new ChargeTransactionRequest(
                userId, orderId, BigDecimal.TEN
        );

        int numOfThread = 20;
        ExecutorService service = Executors.newFixedThreadPool(numOfThread);
        AtomicInteger completedTasks = new AtomicInteger(0);

        for (int i = 0; i < numOfThread; i++) {
            service.submit(() -> {
                try {
                    ChargeTransactionResponse response = transactionService.charge(chargeTransactionRequest);
                    System.out.println(response);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    completedTasks.incrementAndGet();
                }
            });
        }

        service.shutdown();
        boolean finished = service.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(finished);

    }
}