package org.example.ex03;

import io.reactivex.rxjava3.processors.PublishProcessor;

public class HotPublisherEx {

    public static void main(String[] args) {
        PublishProcessor<Integer> processor = PublishProcessor.create();
        processor.subscribe(data -> System.out.println("구독자1: " + data));
        processor.onNext(1);
        processor.onNext(3);

        processor.subscribe(data -> System.out.println("구독자2: " + data));
        processor.onNext(5);
        processor.onNext(7);

        processor.onComplete();
    }
}
