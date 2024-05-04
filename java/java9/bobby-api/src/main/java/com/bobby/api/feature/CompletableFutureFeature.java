package com.bobby.api.feature;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureFeature {

    public void timeout() throws Exception {
        Runnable runnable = () -> {
            try {
                Thread.sleep(10_000L);
                System.out.println(System.currentTimeMillis() + " - 작업 완료");
            } catch (InterruptedException e) {

            }
        };

        System.out.println(System.currentTimeMillis() + " - 작업 실행");
        CompletableFuture<Void> future = CompletableFuture.runAsync(runnable)
//                .orTimeout(1, TimeUnit.SECONDS);
                .completeOnTimeout(null, 1, TimeUnit.SECONDS);

        future.get();
    }

    public void delay() throws Exception {
        Executor executor = CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS);
        Runnable runnable = () -> {
            System.out.println(System.currentTimeMillis() + " - 작업 완료");
        };

        System.out.println(System.currentTimeMillis() + " - 작업 실행");
        CompletableFuture<Void> future = CompletableFuture.runAsync(runnable, executor);

        future.get();
    }
}
