package org.example.ex02;

import io.reactivex.rxjava3.core.Observable;

public class MarbleExample01 {

    public static void main(String[] args) {
        Observable.just(1, 25, 9, 15, 7, 30)
                .filter(x -> x > 10)
                .subscribe(System.out::println);
    }
}
