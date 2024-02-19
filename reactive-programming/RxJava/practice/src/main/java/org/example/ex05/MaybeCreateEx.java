package org.example.ex05;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeEmitter;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import org.example.utils.DateUtil;
import org.example.utils.LogType;
import org.example.utils.Logger;

/**
 * Maybe 클래스를 이용하여 데이터를 통지하는 예제
 */
public class MaybeCreateEx {
    public static void main(String[] args){
        Maybe<String> maybe = Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> emitter) throws Exception {
//                emitter.onSuccess(DateUtil.getNowDate());

                emitter.onComplete();
            }
        });

        maybe.subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                // 아무것도 하지 않음.
            }

            @Override
            public void onSuccess(String data) {
                Logger.log(LogType.ON_SUCCESS, "# 현재 날짜시각: " + data);
            }

            @Override
            public void onError(Throwable error) {
                Logger.log(LogType.ON_ERROR, error);
            }

            @Override
            public void onComplete() {
                Logger.log(LogType.ON_COMPLETE);
            }
        });
    }
}