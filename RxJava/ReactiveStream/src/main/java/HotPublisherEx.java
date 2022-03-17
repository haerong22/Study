import io.reactivex.rxjava3.processors.PublishProcessor;

public class HotPublisherEx {

    public static void main(String[] args) {

        PublishProcessor<Integer> processor = PublishProcessor.create();
        processor.subscribe(data -> System.out.println("subscribe1 = " + data));
        processor.onNext(1);
        processor.onNext(2);

        // 구독한 시점 이후의 데이터만 받을 수 있다.
        processor.subscribe(data -> System.out.println("subscribe2 = " + data));
        processor.onNext(3);
        processor.onNext(4);

    }
}
