package com.bobby.api.flowapi;

import java.util.concurrent.Flow;

public class CoffeeSubscriber implements Flow.Subscriber<String> {

    private Flow.Subscription subscription;
    private int coffeeCount = 0;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1L);
    }

    @Override
    public void onNext(String item) {
        System.out.println("수신 : " + item);
        coffeeCount++;

        if (coffeeCount < 2) {
            subscription.request(1L);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("에러 발생!");
    }

    @Override
    public void onComplete() {
        System.out.println("완료!");
        subscription.cancel();
    }
}
