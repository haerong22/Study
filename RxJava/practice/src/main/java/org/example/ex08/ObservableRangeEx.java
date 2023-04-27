package org.example.ex08;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;

/**
 * 반복문으로 사용 가능
 */
public class ObservableRangeEx {
    public static void main(String[] args){
        Observable<Integer> source = Observable.range(0, 5);
        source.subscribe(num -> Logger.log(LogType.ON_NEXT, num));
    }
}