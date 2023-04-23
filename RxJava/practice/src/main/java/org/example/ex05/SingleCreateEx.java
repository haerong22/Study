package org.example.ex05;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import org.example.utils.DateUtil;
import org.example.utils.LogType;
import org.example.utils.Logger;

public class SingleCreateEx {

    public static void main(String[] args) {
        Single<String> single = Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Throwable {
                emitter.onSuccess(DateUtil.getNowDate());
            }
        });

        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull String s) {
                Logger.log(LogType.ON_SUCCESS, "# 날짜시간: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.log(LogType.ON_ERROR, e);
            }
        });
    }
}
