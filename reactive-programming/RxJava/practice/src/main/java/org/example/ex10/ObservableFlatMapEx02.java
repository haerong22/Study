package org.example.ex10;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;

/**
 * flapMap을 이용한 구구단의 2단 출력 예제
 */
public class ObservableFlatMapEx02 {
    public static void main(String[] args){
        Observable.range(2, 1)
                .flatMap(
                        num -> Observable.range(1, 9)
                                         .map(row -> num + " * " + row + " = " + num * row)
                )
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));
    }
}