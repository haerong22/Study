package org.example.ex11;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.example.utils.LogType;
import org.example.utils.Logger;

import java.util.List;

/**
 * 각각의 통지 데이터를 List로 변환해서 Single로 한번만 통지하는 예제
 */
public class ObservableToListEx01 {
    public static void main(String[] args) {
        Single<List<Integer>> single = Observable.just(1, 3, 5, 7, 9)
                                                .toList();

        single.subscribe(data -> Logger.log(LogType.ON_NEXT, data));
    }
}