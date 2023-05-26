package org.example.ex10;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;
import org.example.utils.TimeUtil;

import java.util.concurrent.TimeUnit;

/**
 * 순서를 보장해주는 concatMap 예제
 * 순차적으로 실행되기때문에 flatMap보다 느리다.
 */
public class ObservableConcatMapEx01 {
    public static void main(String[] args) {
        TimeUtil.start();
        Observable.interval(100L, TimeUnit.MILLISECONDS)
                .take(4)
                .skip(2)
                .concatMap(
                        num -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                                .take(10)
                                .skip(1)
                                .map(row -> num + " * " + row + " = " + num * row)
                ).subscribe(
                        data -> Logger.log(LogType.ON_NEXT, data),
                        error -> {},
                        () -> {
                            TimeUtil.end();
                            TimeUtil.takeTime();
                        }
                );

        TimeUtil.sleep(5000L);
    }
}