package _01_basic;

import io.reactivex.rxjava3.core.Flowable;

public class OperatorEx {

    public static void main(String[] args) {

        Flowable<Integer> flowable =
                Flowable.just(1, 2, 3, 4, 5, 6, 7, 8)
                        .filter(data -> data % 2 == 0 )
                        .map(data -> data * 2);

        flowable.subscribe(data -> System.out.println("data = " + data));
    }
}
