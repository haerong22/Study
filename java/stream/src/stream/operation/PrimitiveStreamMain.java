package stream.operation;

import java.util.IntSummaryStatistics;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class PrimitiveStreamMain {

    public static void main(String[] args) {

        // 기본형 특화 스트림(IntStream, LongStream, DoubleStream)
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        stream.forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        // 범위 생성 메서드(IntStream, LongStream)
        IntStream range1 = IntStream.range(1, 6); // [1, 2, 3, 4, 5]
        range1.forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        IntStream range2 = IntStream.rangeClosed(1, 5); // [1, 2, 3, 4, 5]
        range2.forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        // 1. 통계 관련 메서드(sum, average, max, min, count)
        int sum = IntStream.range(1, 6).sum();
        System.out.println("sum = " + sum);
        System.out.println();

        double avg = IntStream.range(1, 6)
                .average()
                .getAsDouble();
        System.out.println("avg = " + avg);
        System.out.println();

        IntSummaryStatistics stats = IntStream.range(1, 6).summaryStatistics();
        System.out.println("합계: " + stats.getSum());
        System.out.println("평균: " + stats.getAverage());
        System.out.println("최대값: " + stats.getMax());
        System.out.println("최소값: " + stats.getMin());
        System.out.println("개수: " + stats.getCount());
        System.out.println();

        // 2. 타입 변환 메서드
        LongStream longStream = IntStream.range(1, 5).asLongStream();
        longStream.forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        DoubleStream doubleStream = IntStream.range(1, 5).asDoubleStream();
        doubleStream.forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        Stream<Integer> boxedStream = IntStream.range(1, 5).boxed();
        boxedStream.forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        // 3. 기본형 특화 매핑
        LongStream mappedLong = IntStream.range(1, 5)
                .mapToLong(i -> i * 10L);
        mappedLong.forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        DoubleStream mappedDouble = IntStream.range(1, 5)
                .mapToDouble(i -> i * 1.5);
        mappedDouble.forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        Stream<String> mappedObj = IntStream.range(1, 5)
                .mapToObj(i -> "Number: " + i);
        mappedObj.forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        // 4. 객체 스트림 -> 기본형 특화 스트림으로 매핑
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);
        IntStream intStream = integerStream.mapToInt(i -> i);
        intStream.forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        // 5. 객체 스트림 -> 기본형 특화 스트림 매핑 활용
        int result = Stream.of(1, 2, 3, 4, 5)
                .mapToInt(i -> i)
                .sum();
        System.out.println("result = " + result);
    }

    /*
        1 2 3 4 5

        1 2 3 4 5

        1 2 3 4 5

        sum = 15

        avg = 3.0

        합계: 15
        평균: 3.0
        최대값: 5
        최소값: 1
        개수: 5

        1 2 3 4

        1.0 2.0 3.0 4.0

        1 2 3 4

        10 20 30 40

        1.5 3.0 4.5 6.0

        Number: 1 Number: 2 Number: 3 Number: 4

        1 2 3 4 5

        result = 15
     */
}
