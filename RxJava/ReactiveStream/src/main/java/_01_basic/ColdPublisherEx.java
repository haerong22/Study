package _01_basic;

import io.reactivex.rxjava3.core.Flowable;

public class ColdPublisherEx {

    public static void main(String[] args) {

        Flowable<Integer> flowable = Flowable.just(1, 2, 3, 4);

        // 구독시 처음부터 타임라인을 재구성하여 모든 데이터를 받을 수 있다.
        flowable.subscribe(data -> System.out.println("data = " + data));
        flowable.subscribe(data -> System.out.println("data = " + data));
    }
}
