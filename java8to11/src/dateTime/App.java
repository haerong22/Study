package dateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class App {

    public static void main(String[] args) throws InterruptedException {

        /**
         * java 8 의 Date-Time API
         * - Clear
         * - Fluent
         * - Immutable
         * - Extensible
         */
        

        // 기존의 Date, Time 관련 문제점
        Date date = new Date();
        long time = date.getTime(); // epoch time
        System.out.println(date);
        System.out.println(time);

        // date 변경이 가능 ( mutable ) -> thread safe 하지 않다.
        Thread.sleep(1000 * 3);
        Date after3Seconds = new Date();
        System.out.println(after3Seconds);
        after3Seconds.setTime(time);
        System.out.println(after3Seconds);

        // month 가 0부터 시작 : 8월 이면 7, int 값을 받는다. -> type safe 하지 않다.
        Calendar birthDay = new GregorianCalendar(2092, Calendar.AUGUST, 8);
        System.out.println(birthDay.getTime());
        birthDay.add(Calendar.DAY_OF_YEAR, 1);
        System.out.println(birthDay.getTime());
    }
}
