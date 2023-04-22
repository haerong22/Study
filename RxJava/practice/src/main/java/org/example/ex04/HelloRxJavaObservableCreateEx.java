package org.example.ex04;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.example.utils.LogType;
import org.example.utils.Logger;

public class HelloRxJavaObservableCreateEx {
    public static void main(String[] args) throws InterruptedException {
        Observable<String> observable =
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        String[] datas = {"Hello", "RxJava!"};
                        for (String data : datas) {
                            if (emitter.isDisposed())
                                return;

                            emitter.onNext(data);
                        }
                        emitter.onComplete();
                    }
                });

        observable.observeOn(Schedulers.computation())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        // 아무 처리도 하지 않음.
                    }

                    @Override
                    public void onNext(String data) {
                        Logger.log(LogType.ON_NEXT, data);
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

        Thread.sleep(500L);
    }
}