package org.example.ex09;

import io.reactivex.rxjava3.core.Observable;
import org.example.common.SampleData;
import org.example.utils.LogType;
import org.example.utils.Logger;

/**
 * 이미 통지한 데이터와 같은 데이터는 제외하고 통지
 * 유일한 값을 처리하고자 할때 사용
 */
public class ObservableDistinctEx01 {
    public static void main(String[] args) {
        Observable.fromArray(SampleData.carMakersDuplicated)
                .distinct()
                .subscribe(carMaker -> Logger.log(LogType.ON_NEXT, carMaker));
    }
}