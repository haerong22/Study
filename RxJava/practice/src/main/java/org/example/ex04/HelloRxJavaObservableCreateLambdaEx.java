package org.example.ex04;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.example.utils.LogType;
import org.example.utils.Logger;

public class HelloRxJavaObservableCreateLambdaEx {
    public static void main(String[] args) throws InterruptedException {
        Observable<String> observable =
                Observable.create(emitter -> {
                    String[] datas = {"Hello", "RxJava!"};
                    for (String data : datas) {
                        if (emitter.isDisposed())
                            return;

                        emitter.onNext(data);
                    }
                    emitter.onComplete();
                });

        observable.observeOn(Schedulers.computation())
                .subscribe(
                        data -> Logger.log(LogType.ON_NEXT, data),
                        error -> Logger.log(LogType.ON_NEXT, error),
                        () -> Logger.log(LogType.DO_ON_COMPLETE)
                );

        Thread.sleep(500L);
    }
}