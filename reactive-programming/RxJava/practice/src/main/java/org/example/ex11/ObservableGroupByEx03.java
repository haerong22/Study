package org.example.ex11;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.GroupedObservable;
import org.example.common.Car;
import org.example.common.CarMaker;
import org.example.common.SampleData;

/**
 * 제조사를 그룹으로 묶어서 자동차 명을 출력하는 예제
 */
public class ObservableGroupByEx03 {
    public static void main(String[] args) {
        Observable<GroupedObservable<CarMaker, Car>> observable =
                Observable.fromIterable(SampleData.carList)
                        .groupBy(Car::getCarMaker);

        observable
                .flatMapSingle(carGroup ->
                        carGroup.flatMap(car ->
                                Observable.just(car.getCarName()))
                                .toList()
                )
                .subscribe(System.out::println);

    }
}