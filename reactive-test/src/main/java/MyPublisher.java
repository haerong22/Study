import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.Arrays;

public class MyPublisher implements Publisher<Integer> {

    Iterable<Integer> its = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        System.out.println("구독 신청");
        System.out.println("구독 정보 생성");
        MySubscription subscription = new MySubscription(subscriber, its);
        System.out.println("구독 정보 생성완료");
        subscriber.onSubscribe(subscription);
    }
}
