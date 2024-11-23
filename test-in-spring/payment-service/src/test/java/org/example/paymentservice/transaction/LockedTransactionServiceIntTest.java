package org.example.paymentservice.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class LockedTransactionServiceIntTest {
    @Autowired
    LockedTransactionService transactionService;

    @Test
    void 충전을_동시에_실행한다() throws InterruptedException {
        Long userId = 100L;

        int numOfThread = 20;
        ExecutorService service = Executors.newFixedThreadPool(numOfThread);
        AtomicInteger completedTasks = new AtomicInteger(0);

        for (int i = 0; i < numOfThread; i++) {
            String orderId = UUID.randomUUID().toString();
            ChargeTransactionRequest chargeTransactionRequest = new ChargeTransactionRequest(
                    userId, orderId, BigDecimal.TEN
            );

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