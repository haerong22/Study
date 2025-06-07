package parallel;

import java.util.stream.IntStream;

import static util.MyLogger.log;

public class ParallelMain1 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        int sum = IntStream.rangeClosed(1, 8)
                .map(HeavyJob::heavyTask)
                .reduce(0, Integer::sum);

        long endTime = System.currentTimeMillis();
        log("time: " + (endTime - startTime) + "ms, sum: " + sum);
    }

    /*
        23:07:44.789 [     main] calculate 1 -> 10
        23:07:45.801 [     main] calculate 2 -> 20
        23:07:46.806 [     main] calculate 3 -> 30
        23:07:47.808 [     main] calculate 4 -> 40
        23:07:48.809 [     main] calculate 5 -> 50
        23:07:49.814 [     main] calculate 6 -> 60
        23:07:50.819 [     main] calculate 7 -> 70
        23:07:51.824 [     main] calculate 8 -> 80
        23:07:52.831 [     main] time: 8347ms, sum: 360
     */
}
