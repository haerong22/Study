package _03_basic;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ObservableEx {

    public static void main(String[] args) {

        /*
            observable 은 배압이 없기 때문에 데이터가 생성 될 때마다 바로 통지된다.
         */
        Observable<String> observable = Observable.create(emitter -> {
            String[] data = {"Hello, World!", "Hello, RxJava!"};

            for (String d : data) {
                if (emitter.isDisposed()) {
                    return;
                }
                emitter.onNext(d);
            }

            emitter.onComplete();
        });

        observable.observeOn(Schedulers.computation())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // 구독 중 구독을 해지하려면 전달받은 Disposable 을 내부에 보관(dispose 메소드 호출)
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                });
    }
}
