package org.example.ex11;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.GroupedObservable;
import org.example.common.Car;
import org.example.common.CarMaker;
import org.example.common.SampleData;
import org.example.utils.LogType;
import org.example.utils.Logger;

/**
 * Group으로 묶은 데이터들 중에서 filter를 이용해 필터링한 Group의 데이터만 출력하는 예제
 */
public class ObservableGroupByEx02 {
    public static void main(String[] args) {
        Observable<GroupedObservable<CarMaker, Car>> observable =
                Observable.fromIterable(SampleData.carList).groupBy(Car::getCarMaker);

        observable.subscribe(
                groupedObservable ->
                        groupedObservable
                                .filter(car -> groupedObservable.getKey().equals(CarMaker.CHEVROLET))
                                .subscribe(
                                        car -> Logger.log(
                                                LogType.PRINT, "Group: "
                                                        + groupedObservable.getKey()
                                                        + "\t Car name: " + car.getCarName())
                                )
        );

    }
}