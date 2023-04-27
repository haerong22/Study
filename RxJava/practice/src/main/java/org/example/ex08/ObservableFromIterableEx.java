package org.example.ex08;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;

import java.util.Arrays;
import java.util.List;

public class ObservableFromIterableEx {
    public static void main(String[] args){
        List<String> countries = Arrays.asList("Korea", "Canada", "USA", "Italy");

        Observable.fromIterable(countries)
                .subscribe(country -> Logger.log(LogType.ON_NEXT, country));
    }
}