package org.example.counter;

public class Counter implements Runnable {
    private int count = 0;

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public int getValue() {
        return count;
    }

    @Override
    public void run() {
        synchronized (this) {
            this.increment();
            System.out.println("Value for Thread After increment " + Thread.currentThread().getName() + " " + this.getValue());

            this.decrement();
            System.out.println("Value for Thread at Last " + Thread.currentThread().getName() + " " + this.getValue());
        }
    }
}
