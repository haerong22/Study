package org.example.ex05;

import io.reactivex.rxjava3.core.Single;
import org.example.utils.DateUtil;
import org.example.utils.LogType;
import org.example.utils.Logger;

public class SingleJustEx {

    public static void main(String[] args) {
        Single.just(DateUtil.getNowDate())
                .subscribe(
                        data -> Logger.log(LogType.ON_SUCCESS, "# 날짜시간: " + data),
                        error -> Logger.log(LogType.ON_ERROR, error)
                );
    }
}
