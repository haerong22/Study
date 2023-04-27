package org.example.ex08;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;
import org.example.utils.TimeUtil;

import java.util.concurrent.TimeUnit;

/**
 * 설정한 시간이 지난 후에 특정 동작을 수행하고자 할때 사용
 */
public class ObservableTimerEx {
    public static void main(String[] args){
        Logger.log(LogType.PRINT, "# Start!");
        Observable<String> observable =
                Observable.timer(2000, TimeUnit.MILLISECONDS)
                        .map(count -> "Do work!");

        observable.subscribe(data -> Logger.log(LogType.ON_NEXT, data));

        TimeUtil.sleep(3000);
    }
}