package com.example.api.service;

import com.example.api.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    ApplyService applyService;

    @Autowired
    CouponRepository couponRepository;

    @Test
    void 한번만_응모() {
        applyService.apply(1L);

        long count = couponRepository.count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    void 동시에_응모() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    applyService.apply(userId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Thread.sleep(10000);

        long count = couponRepository.count();

        assertThat(count).isEqualTo(100);
    }

    @Test
    void 한명당_한개의_쿠폰만_발급() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    applyService.apply(1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Thread.sleep(10000);

        long count = couponRepository.count();

        assertThat(count).isEqualTo(1);
    }
}