package org.example.ex01;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReactiveProgramming {

    public static void main(String[] args) throws InterruptedException {
        Observable.just(100, 200, 300, 400, 500)
                .doOnNext(data -> System.out.println(Thread.currentThread().getName() + " : #doOnNext() : " +  data))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .filter(number -> number > 300)
                .subscribe(num -> System.out.println(Thread.currentThread().getName() + " : result : " + num));

        Thread.sleep(500);
    }
}
