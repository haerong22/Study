package org.example.ex05;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.example.utils.LogType;
import org.example.utils.Logger;
import org.example.utils.TimeUtil;

/**
 * Completable 을 사용하여 어떤 작업을 수행한 후, 완료를 통지하는 예제
 */
public class CompletableCreateEx {
    public static void main(String[] args) throws InterruptedException {
        Completable completable = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                // 데이터를 통지하는것이 아니라 특정 작업을 수행한 후, 완료를 통지한다.
                int sum = 0;
                for(int i =0; i < 100; i++){
                    sum += i;
                }
                Logger.log(LogType.PRINT, "# 합계: " + sum);

                emitter.onComplete();
            }
        });

        completable.subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable disposable) {
                // 아무것도 하지 않음
            }

            @Override
            public void onComplete() {
                Logger.log(LogType.ON_COMPLETE);
            }

            @Override
            public void onError(Throwable error) {
                Logger.log(LogType.ON_ERROR, error);
            }
        });

        TimeUtil.sleep(1000L);
    }
}