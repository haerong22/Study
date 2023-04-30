package org.example.ex09;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;

/**
 * 지정한 갯수만큼 데이터를 발행
 */
public class ObservableTakeEx01 {
    public static void main(String[] args) {
        Observable.just("a", "b", "c", "d")
                .take(2)
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));
    }
}