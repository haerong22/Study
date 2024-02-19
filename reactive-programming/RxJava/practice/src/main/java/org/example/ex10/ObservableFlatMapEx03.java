package org.example.ex10;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;

/**
 * FlatMap 두번째 유형을 이용한 구구단의 2단 출력 예제
 */
public class ObservableFlatMapEx03 {
    public static void main(String[] args) {
        Observable.range(2, 1)
                .flatMap(
                        data -> Observable.range(1, 9),
                        (sourceData, transformedData) ->
                                sourceData + " * " + transformedData + " = " + sourceData * transformedData
                )
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));

    }
}