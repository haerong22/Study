package org.example.ex08;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;
import org.example.utils.TimeUtil;

import java.util.concurrent.TimeUnit;

/**
 * polling 용도로 주로 사용.
 */
public class ObservableIntervalEx {
    public static void main(String[] args){
        System.out.println("# start : " + TimeUtil.getCurrentTimeFormatted());

        Observable.interval(0, 1000L, TimeUnit.MILLISECONDS)
                .map(num -> num + " count")
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));

        TimeUtil.sleep(3000);
    }
}