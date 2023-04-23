package org.example.ex05;

import io.reactivex.rxjava3.core.Single;
import org.example.utils.DateUtil;
import org.example.utils.LogType;
import org.example.utils.Logger;

public class SingleCreateLambdaEx {

    public static void main(String[] args) {
        Single<String> single = Single.create(emitter -> emitter.onSuccess(DateUtil.getNowDate()));

        single.subscribe(
                data -> Logger.log(LogType.ON_SUCCESS, "# 날짜시간: " + data),
                error -> Logger.log(LogType.ON_ERROR, error)
        );
    }
}
