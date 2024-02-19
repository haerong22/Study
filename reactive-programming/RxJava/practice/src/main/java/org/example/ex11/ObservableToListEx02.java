package org.example.ex11;

import io.reactivex.rxjava3.core.Observable;
import org.example.common.SampleData;
import org.example.utils.LogType;
import org.example.utils.Logger;

/**
 * 각각의 통지될 Car 객체를 List로 변환해서 Single로 한번만 통지하는 예제
 */
public class ObservableToListEx02 {
    public static void main(String[] args) {
        Observable.fromIterable(SampleData.carList)
                .toList()
                .subscribe(carList -> Logger.log(LogType.ON_NEXT, carList));
    }
}