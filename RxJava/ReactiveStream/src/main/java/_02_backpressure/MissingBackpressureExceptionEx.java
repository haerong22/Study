package _02_backpressure;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class MissingBackpressureExceptionEx {

    public static void main(String[] args) throws InterruptedException {
        /*
           실행시 통지 속도보다 처리 속도가 느리기 때문에 MissingBackpressureException 발생
           기본 버퍼가 128 이므로 128이상의 통지가 오면 예외발생
         */
        Flowable.interval(1L, TimeUnit.MILLISECONDS) // 1ms 마다 숫자 통지
                .doOnNext(data -> System.out.println("data = " + data)) // 통지된 데이터 출력
                .observeOn(Schedulers.computation())
                .subscribe(
                        data -> {
                            System.out.println("처리 대기중");
                            Thread.sleep(1000L); // 1초의 지연
                            System.out.println("data = " + data);
                        },
                        error -> System.out.println("error = " + error),
                        () -> System.out.println("completed")
                );

        Thread.sleep(2000L);
    }
}
