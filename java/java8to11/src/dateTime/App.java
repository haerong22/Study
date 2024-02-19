package dateTime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class App {

    public static void main(String[] args) throws InterruptedException {

        /**
         * java 8 의 Date-Time API
         * - Clear
         * - Fluent
         * - Immutable
         * - Extensible
         */

        // immutable 하므로 새로운 인스턴스를 생성한다.
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plus = now.plus(10, ChronoUnit.YEARS);
        System.out.println(now);
        System.out.println(plus);

        System.out.println("==========================================");

        // 레거시 API 지원
        Date date = new Date();
        Instant instant = date.toInstant();
        Date newDate = Date.from(instant);
        System.out.println(date);
        System.out.println(instant);
        System.out.println(newDate);

        System.out.println("==========================================");

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        ZonedDateTime zonedDateTime = gregorianCalendar.toZonedDateTime();
        LocalDateTime localDateTime = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(zonedDateTime);
        System.out.println(localDateTime);

        GregorianCalendar from = GregorianCalendar.from(zonedDateTime);

        System.out.println(from);

        ZoneId zoneId = TimeZone.getTimeZone("PST").toZoneId();
        TimeZone timeZone = TimeZone.getTimeZone(zoneId);

        System.out.println(zoneId);
        System.out.println(timeZone);

        System.out.println("==========================================");


//        // formatting
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now);
//        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        System.out.println(now.format(pattern));

//        // parsing
//        LocalDate parse = LocalDate.parse("08/23/1990", pattern);
//        System.out.println(parse);
//
//        System.out.println("==========================================");


//        // 기간을 표현하는 방법
//        LocalDate today = LocalDate.now();
//        LocalDate thisYearBirthday = LocalDate.of(2021, Month.AUGUST, 23);
//
//        Period period = Period.between(today, thisYearBirthday);
//        System.out.println(period.getDays());
//
//        // 사람용 시간 비교 Period
//        Period until = today.until(thisYearBirthday);
//        System.out.println(until.get(ChronoUnit.DAYS));
//
//        System.out.println("==========================================");
//
//        // 기계용 시간 비교 Duration
//        Instant now = Instant.now();
//        Instant plus = now.plus(10, ChronoUnit.SECONDS);
//        Duration between = Duration.between(now, plus);
//        System.out.println(between.getSeconds());
//
//        System.out.println("==========================================");

//        // 사람용 시간 표현 방법
//        LocalDateTime now = LocalDateTime.now(); // Local -> 현재 시스템의 Zone 정보를 이용하여 시간 표시
//        System.out.println(now);
//        LocalDateTime birthDay = LocalDateTime.of(1990, Month.AUGUST, 23, 0, 0, 0);
//        System.out.println(birthDay);
//
//        System.out.println("==========================================");
//
//        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul")); // 지정한 Zone 의 시간
//        System.out.println(nowInKorea);
//
//        Instant nowInstant = Instant.now();
//        ZonedDateTime zonedDateTime = nowInstant.atZone(ZoneId.of("Asia/Seoul"));
//        System.out.println(zonedDateTime);
//
//        System.out.println("==========================================");



//        // 현재 시간을 기계 시간으로 표현하는 방법
//       Instant instant = Instant.now();
//        System.out.println(instant); // 기준시 UTC, GMT
//        System.out.println(instant.atZone(ZoneId.of("UTC"))); // 기준시 UTC, GMT
//
//        ZoneId zone = ZoneId.systemDefault();
//        System.out.println(zone); // 현재 시스템 기준 위치
//        ZonedDateTime zonedDateTime = instant.atZone(zone);
//        System.out.println(zonedDateTime); // zone 기준 시간

//        // 기존의 Date, Time 관련 문제점
//        Date date = new Date();
//        long time = date.getTime(); // epoch time
//        System.out.println(date);
//        System.out.println(time);
//
//        // date 변경이 가능 ( mutable ) -> thread safe 하지 않다.
//        Thread.sleep(1000 * 3);
//        Date after3Seconds = new Date();
//        System.out.println(after3Seconds);
//        after3Seconds.setTime(time);
//        System.out.println(after3Seconds);
//
//        // month 가 0부터 시작 : 8월 이면 7, int 값을 받는다. -> type safe 하지 않다.
//        Calendar birthDay = new GregorianCalendar(2092, Calendar.AUGUST, 8);
//        System.out.println(birthDay.getTime());
//        birthDay.add(Calendar.DAY_OF_YEAR, 1);
//        System.out.println(birthDay.getTime());
    }
}
