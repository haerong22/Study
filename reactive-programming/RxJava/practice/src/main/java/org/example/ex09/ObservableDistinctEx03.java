package org.example.ex09;

import io.reactivex.rxjava3.core.Observable;
import org.example.common.Car;
import org.example.common.SampleData;
import org.example.utils.LogType;
import org.example.utils.Logger;

/**
 * 객체의 특정 필드를 기준으로 distinct 하는 예제
 */
public class ObservableDistinctEx03 {
    public static void main(String[] args) {
        Observable.fromIterable(SampleData.carList)
                .distinct(Car::getCarMaker)
                .subscribe(car -> Logger.log(LogType.ON_NEXT, car.getCarName()));

    }
}