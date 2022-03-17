import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class AsyncEx {

    private enum State {
        ADD, MULTIPLY
    }

    private static State calcMethod;

    public static void main(String[] args) throws InterruptedException {

        calcMethod = State.ADD;

        Flowable<Long> flowable =
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                        .take(7)
                        .scan((sum, data) -> {
                            if (calcMethod == State.ADD) {
                                return sum + data;
                            } else {
                                return sum * data;
                            }
                        });

        // 구독 후 받은 데이터 출력
        flowable.subscribe(data -> System.out.println("data = " + data));

        // Sleep 후 계산법 변경
        // Bad case -> 외부의 데이터가 계산에 영향을 주면 안된다.
        Thread.sleep(1000);
        System.out.println("계산법 변경");
        calcMethod = State.MULTIPLY;

        Thread.sleep(2000);
    }
}
