package org.example.ex11;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.GroupedObservable;
import org.example.common.Car;
import org.example.common.CarMaker;
import org.example.common.SampleData;
import org.example.utils.LogType;
import org.example.utils.Logger;

/**
 * Car 제조사 별로 그룹으로 묶어서 데이터를 통지하는 예제
 */
public class ObservableGroupByEx01 {
    public static void main(String[] args) {
        Observable<GroupedObservable<CarMaker, Car>> observable =
                Observable.fromIterable(SampleData.carList).groupBy(Car::getCarMaker);

        observable.subscribe(
                groupedObservable -> groupedObservable.subscribe(
                        car -> Logger.log(
                                LogType.ON_NEXT, "Group: " +
                                        groupedObservable.getKey() +
                                        "\t Car name: " + car.getCarName())
                )
        );

    }
}