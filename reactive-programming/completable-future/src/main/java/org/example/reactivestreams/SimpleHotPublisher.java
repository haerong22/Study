package org.example.reactivestreams;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

@Slf4j
public class SimpleHotPublisher implements Flow.Publisher<Integer> {

    private final ExecutorService publisherExecutor = Executors.newSingleThreadExecutor();
    private final Future<Void> task;
    private final List<Integer> numbers = new ArrayList<>();
    private final List<SimpleHotSubscription> subscriptions = new ArrayList<>();

    public SimpleHotPublisher() {
        numbers.add(1);

        task = publisherExecutor.submit(() -> {
            for (int i = 2; !Thread.interrupted(); i++) {
                numbers.add(i);
                subscriptions.forEach(SimpleHotSubscription::wakeup);
                Thread.sleep(100);
            }
            return null;
        });
    }

    public void shutdown() {
        this.task.cancel(true);
        publisherExecutor.shutdown();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        var subscription = new SimpleHotSubscription(numbers.size() - 1, subscriber);
        subscriber.onSubscribe(subscription);
        subscriptions.add(subscription);
    }

    private class SimpleHotSubscription implements Flow.Subscription {
        private int offset;
        private int requiredOffset;
        private final Flow.Subscriber<? super Integer> subscriber;
        private final ExecutorService subscriptionExecutorService = Executors.newSingleThreadExecutor();

        public SimpleHotSubscription(int lastElementIndex, Flow.Subscriber<? super Integer> subscriber) {
            this.offset = lastElementIndex;
            this.requiredOffset = lastElementIndex;
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            requiredOffset += n;

            onNextWhilePossible();
        }

        @Override
        public void cancel() {
            this.subscriber.onComplete();
            subscriptions.remove(this);
            subscriptionExecutorService.shutdown();
        }

        public void wakeup() {
            onNextWhilePossible();
        }

        private void onNextWhilePossible() {
            subscriptionExecutorService.submit(() -> {
                while (offset < requiredOffset && offset < numbers.size()) {
                    var item = numbers.get(offset);
                    subscriber.onNext(item);
                    offset++;
                }
            });
        }
    }
}
