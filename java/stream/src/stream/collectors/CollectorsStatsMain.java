package stream.collectors;

import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CollectorsStatsMain {

    public static void main(String[] args) {
        Long count1 = Stream.of(1, 2, 3)
                .collect(Collectors.counting());
        System.out.println("count1 = " + count1);

        long count2 = Stream.of(1, 2, 3)
                .count();
        System.out.println("count2 = " + count2);

        Double average1 = Stream.of(1, 2, 3)
                .collect(Collectors.averagingInt(i -> i));
        System.out.println("average1 = " + average1);

        double average2 = Stream.of(1, 2, 3)
                .mapToInt(i -> i)
                .average()
                .getAsDouble();
        System.out.println("average2 = " + average2);

        double average3 = IntStream.of(1, 2, 3)
                .average()
                .getAsDouble();
        System.out.println("average3 = " + average3);

        IntSummaryStatistics stats = Stream.of("Apple", "Banana", "Tomato")
                .collect(Collectors.summarizingInt(String::length));
        System.out.println("stats.getCount() = " + stats.getCount());
        System.out.println("stats.getSum() = " + stats.getSum());
        System.out.println("stats.getMin() = " + stats.getMin());
        System.out.println("stats.getMax() = " + stats.getMax());
        System.out.println("stats.getAverage() = " + stats.getAverage());
    }
    /*
        count1 = 3
        count2 = 3
        average1 = 2.0
        average2 = 2.0
        average3 = 2.0
        stats.getCount() = 3
        stats.getSum() = 17
        stats.getMin() = 5
        stats.getMax() = 6
        stats.getAverage() = 5.666666666666667
     */
}
