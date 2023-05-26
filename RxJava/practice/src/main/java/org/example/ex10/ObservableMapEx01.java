package org.example.ex10;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Observable이 통지한 항목에 함수를 적용하여 통지된 값을 변환시킨다.
 */
public class ObservableMapEx01 {
    public static void main(String[] args){
        List<Integer> oddList = Arrays.asList(1, 3, 5, 7);
        Observable.fromIterable(oddList)
                .map(num -> "1을 더한 결과: " + (num + 1))
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));
    }
}