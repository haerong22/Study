package org.example.ex08;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;
import org.example.utils.TimeUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ObservableFromFutureEx {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Logger.log(LogType.PRINT, "# start time");

        // 긴 처리 시간이 걸리는 작업
        Future<Double> future = longTimeWork();

        // 짧은 처리 시간이 걸리는 작업
        shortTimeWork();

        Observable.fromFuture(future)
                .subscribe(data -> Logger.log(LogType.PRINT, "# 긴 처리 시간 작업 결과 : " + data));

        Logger.log(LogType.PRINT, "# end time");
    }



    public static CompletableFuture<Double> longTimeWork(){
        return CompletableFuture.supplyAsync(ObservableFromFutureEx::calculate);
    }

    private static Double calculate() {
        Logger.log(LogType.PRINT, "# 긴 처리 시간이 걸리는 작업 중.........");
        TimeUtil.sleep(6000L);
        return 100000000000000000.0;
    }

    private static void shortTimeWork() {
        TimeUtil.sleep(3000L);
        Logger.log(LogType.PRINT, "# 짧은 처리 시간 작업 완료!");
    }
}