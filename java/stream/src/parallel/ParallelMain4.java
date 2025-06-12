package parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static util.MyLogger.log;

public class ParallelMain4 {

    public static void main(String[] args) {
        int processorCount = Runtime.getRuntime().availableProcessors();
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        log("processorCount = " + processorCount + ", commonPool = " + commonPool.getParallelism());

        long startTime = System.currentTimeMillis();

        int sum = IntStream.rangeClosed(1, 8)
                .parallel()
                .map(HeavyJob::heavyTask)
                .reduce(0, Integer::sum);

        long endTime = System.currentTimeMillis();
        log("time: " + (endTime - startTime) + "ms, sum: " + sum);
    }

    /*
        23:25:04.687 [     main] processorCount = 8, commonPool = 7
        23:25:04.707 [     main] calculate 6 -> 60
        23:25:04.708 [ForkJoinPool.commonPool-worker-1] calculate 3 -> 30
        23:25:04.708 [ForkJoinPool.commonPool-worker-4] calculate 5 -> 50
        23:25:04.708 [ForkJoinPool.commonPool-worker-7] calculate 7 -> 70
        23:25:04.708 [ForkJoinPool.commonPool-worker-5] calculate 4 -> 40
        23:25:04.708 [ForkJoinPool.commonPool-worker-2] calculate 8 -> 80
        23:25:04.708 [ForkJoinPool.commonPool-worker-6] calculate 1 -> 10
        23:25:04.708 [ForkJoinPool.commonPool-worker-3] calculate 2 -> 20
        23:25:05.717 [     main] time: 1025ms, sum: 360
     */
}
