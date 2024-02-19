package _02_backpressure;

import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BackPressureStrategyEx {
    /*
        BUFFER : 모든 데이터 버퍼링
        DROP   : 새로 생성한 데이터 삭제
        LATEST : 버퍼가 비워질 때 까지 통지 데이터 대기
        ERROR  : 버퍼 크기 초과시 MissingBackpressureException
        NONE   : 특정 처리 수행 X
     */
    public static void main(String[] args) throws InterruptedException {
        // BUFFER 전략
//        bufferStrategy();

        // DROP 전략
//        dropStrategy();

        // LATEST 전략
        latestStrategy();

        Thread.sleep(3000L);
    }

    private static void latestStrategy() {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> System.out.println("interval = " + data))
                .onBackpressureLatest()
                .observeOn(Schedulers.computation(), false, 1)
                .subscribe(
                        data -> {
                            Thread.sleep(1000L);
                            System.out.println("data = " + data);
                        },
                        error -> System.out.println("error = " + error)
                );
    }

    private static void dropStrategy() {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> System.out.println("interval = " + data))
                .onBackpressureDrop(drop -> System.out.println("drop = " + drop))
                .observeOn(Schedulers.computation(), false, 1)
                .subscribe(
                        data -> {
                            Thread.sleep(1000L);
                            System.out.println("data = " + data);
                        },
                        error -> System.out.println("error = " + error)
                );
    }

    private static void bufferStrategy() {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> System.out.println("interval = " + data))
                .onBackpressureBuffer(
                        2,
                        () -> System.out.println("overflow"),
                        BackpressureOverflowStrategy.DROP_LATEST // ERROR, DROP_LATEST, DROP_OLDEST
                )
                .doOnNext(data -> System.out.println("buffer = " + data))
                .observeOn(Schedulers.computation(), false, 1)
                .subscribe(
                        data -> {
                            Thread.sleep(1000L);
                            System.out.println("data = " + data);
                        },
                        error -> System.out.println("error = " + error)
                );
    }
}
