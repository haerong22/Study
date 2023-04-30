package org.example.ex09;

import io.reactivex.rxjava3.core.Observable;
import org.example.common.CarMaker;
import org.example.common.SampleData;
import org.example.utils.LogType;
import org.example.utils.Logger;

public class ObservableFilterEx01 {
    public static void main(String[] args) {
        Observable.fromIterable(SampleData.carList)
                .filter(car -> car.getCarMaker() == CarMaker.CHEVROLET)
                .subscribe(car -> Logger.log(LogType.ON_NEXT, car.getCarMaker() + " : " + car.getCarName()));
    }
}