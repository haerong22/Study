package org.example.ex11;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observables.GroupedObservable;
import org.example.common.Car;
import org.example.common.CarMaker;
import org.example.common.SampleData;

/**
 * 제조사 별로 그룹으로 묶은 후, 제조사 별 차량 가격의 합계를 구하는 예제
 */
public class ObservableGroupByEx04 {
    public static void main(String[] args)  {
        Observable<GroupedObservable<CarMaker, Car>> observable =
                Observable.fromIterable(SampleData.carList)
                        .groupBy(Car::getCarMaker);

        observable
                .flatMapSingle(carGroup ->
                        Single.just(carGroup.getKey())
                                .zipWith(
                                    carGroup.flatMap(car ->
                                            Observable.just(car.getCarPrice()))
                                                    .reduce(Integer::sum)
                                                    .toSingle()
                                    , (key, sum) -> key + ": " + sum
                                )
                )
                .subscribe(System.out::println);
    }
}