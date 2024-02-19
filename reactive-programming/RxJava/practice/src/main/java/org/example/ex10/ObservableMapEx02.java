package org.example.ex10;

import io.reactivex.rxjava3.core.Observable;
import org.example.utils.LogType;
import org.example.utils.Logger;

public class ObservableMapEx02 {
    public static void main(String[] args) {
        Observable.just("korea", "america", "canada", "paris", "japan", "china")
                .filter(country -> country.length() == 5 )
                .map(country -> country.toUpperCase().charAt(0) + country.substring(1))
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));


    }
}