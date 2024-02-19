package org.example.ex05;

import io.reactivex.rxjava3.core.Maybe;
import org.example.utils.DateUtil;
import org.example.utils.LogType;
import org.example.utils.Logger;

public class MaybeLambdaEx {
    public static void main(String[] args){
        Maybe<String> maybe = Maybe.create(emitter -> {
            emitter.onSuccess(DateUtil.getNowDate());
//            emitter.onComplete();
        });

        maybe.subscribe(
                data -> Logger.log(LogType.ON_SUCCESS, "# 현재 날짜시각: " + data),
                error -> Logger.log(LogType.ON_ERROR, error),
                () -> Logger.log(LogType.ON_COMPLETE)
        );
    }
}