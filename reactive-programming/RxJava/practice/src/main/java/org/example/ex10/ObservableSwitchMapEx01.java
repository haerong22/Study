package org.example.ex10;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;
import org.example.utils.TimeUtil;

import java.util.concurrent.TimeUnit;

/**
 * 원본 소스의 처리 속도가 빨라서 현재 처리 중이던 작업을 중단하는 예제
 */
public class ObservableSwitchMapEx01 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("# start : " + TimeUtil.getCurrentTimeFormatted());
        Observable.interval(100L, TimeUnit.MILLISECONDS)
                .take(4)
                .skip(2)
                .doOnNext(data -> Logger.log(LogType.DO_ON_NEXT, data))
                .switchMap(
                        num -> Observable.interval(300L, TimeUnit.MILLISECONDS)
                                        .take(10)
                                        .skip(1)
                                        .map(row -> num + " * " + row + " = " + num * row)
                )
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));

        Thread.sleep(5000);
    }
}