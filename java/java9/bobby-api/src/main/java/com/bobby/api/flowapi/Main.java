package com.bobby.api.flowapi;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CoffeePublisher coffeePublisher = new CoffeePublisher();
        CoffeeSubscriber coffeeSubscriber = new CoffeeSubscriber();

        coffeePublisher.subscribe(coffeeSubscriber);

        Thread.sleep(5000);
    }
}
