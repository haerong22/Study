package org.example.ex09;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;

public class ObservableSkipEx01 {
    public static void main(String[] args) {
        Observable.range(1, 15)
                .skip(3)
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));
    }
}