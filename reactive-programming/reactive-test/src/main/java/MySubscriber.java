import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySubscriber implements Subscriber<Integer> {

    private Subscription s;
    private final int SIZE = 5;
    private int bufferSize = SIZE;

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("구독 정보 받음");
        this.s = subscription;
        s.request(bufferSize); // 요청 개수
    }

    @Override
    public void onNext(Integer integer) {
        System.out.println("구독 데이터 전달 : " + integer);
        bufferSize--;
        if (bufferSize == 0) {
            System.out.println("하루 지남");
            bufferSize = SIZE;
            s.request(bufferSize);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("구독 에러");
    }

    @Override
    public void onComplete() {
        System.out.println("구독 완료");
    }
}
