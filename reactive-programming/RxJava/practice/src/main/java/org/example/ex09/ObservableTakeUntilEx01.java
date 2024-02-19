package org.example.ex09;

import io.reactivex.rxjava3.core.Observable;
import org.example.common.Car;
import org.example.common.SampleData;
import org.example.utils.TimeUtil;

/**
 * 파리미터로 지정한 조건이 될 때까지 데이터를 계속 발행
 */
public class ObservableTakeUntilEx01 {
    public static void main(String[] args) {
        Observable.fromIterable(SampleData.carList)
                .takeUntil((Car car) -> car.getCarName().equals("트랙스"))
                .subscribe(car -> System.out.println(car.getCarName()));

        TimeUtil.sleep(300L);
    }
}