package _03_basic;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class FlowableEx {

    public static void main(String[] args) {

        Flowable<String> flowable = Flowable.create(emitter -> {
            String[] data = {"Hello, World!", "Hello, RxJava!"};

            for (String d : data) {
                if (emitter.isCancelled()) {
                    return;
                }
                emitter.onNext(d);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);

        flowable.observeOn(Schedulers.computation())
                .subscribe(new Subscriber<String>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.subscription.request(1L); // 데이터 개수 요청
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                        this.subscription.request(1L); // 다음에 받을 데이터 개수 요청
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                });
    }
}
