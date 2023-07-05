package org.example.ex11;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.example.utils.LogType;
import org.example.utils.Logger;

import java.util.Map;

/**
 * 원본 데이터를 변환한 값과 각각의 key를 매핑하여 Map으로 통지하는 예제
 */
public class ObservableToMapExample02 {
    public static void main(String[] args) {
        Single<Map<String, String>> single = Observable.just("a-Alpha", "b-Bravo", "c-Charlie", "e-Echo")
                .toMap(
                    data -> data.split("-")[0],
                    data -> data.split("-")[1]
                );

        single.subscribe(map -> Logger.log(LogType.ON_NEXT, map));
    }
}