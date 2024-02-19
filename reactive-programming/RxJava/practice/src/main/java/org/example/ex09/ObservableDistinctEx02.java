package org.example.ex09;

import io.reactivex.rxjava3.core.Observable;
import org.example.common.CarMaker;
import org.example.common.SampleData;
import org.example.utils.LogType;
import org.example.utils.Logger;

public class ObservableDistinctEx02 {
    public static void main(String[] args) {
        Observable.fromArray(SampleData.carMakersDuplicated)
                .distinct()
                .filter(carMaker -> carMaker == CarMaker.SSANGYOUNG)
                .subscribe(carMaker -> Logger.log(LogType.ON_NEXT, carMaker));
    }
}