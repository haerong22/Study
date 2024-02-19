package org.example.ex04;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.example.utils.LogType;
import org.example.utils.Logger;
import org.example.utils.TimeUtil;

import java.util.concurrent.TimeUnit;

public class BackpressureDropEx {

    public static void main(String[] args) {
        System.out.println("# start : " + TimeUtil.getCurrentTimeFormatted());
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> Logger.log("#interval doOnNext()", data))
                .onBackpressureDrop(dropData -> Logger.log(LogType.PRINT, dropData + " Drop!"))
                .observeOn(Schedulers.computation(), false, 1)
                .subscribe(
                        data -> {
                            TimeUtil.sleep(1000L);
                            Logger.log(LogType.ON_NEXT, data);
                        },
                        error -> Logger.log(LogType.ON_ERROR, error)
                );

        TimeUtil.sleep(5500L);
    }
}
