package org.example.ex09;

import io.reactivex.rxjava3.core.Observable;
import org.example.common.CarMaker;
import org.example.common.SampleData;

public class ObservableFilterEx02 {
    public static void main(String[] args) {
        Observable.fromIterable(SampleData.carList)
                .filter(car -> car.getCarMaker() == CarMaker.CHEVROLET)
                .filter(car -> car.getCarPrice() > 30000000)
                .subscribe(car -> System.out.println(car.getCarName()));
    }
}