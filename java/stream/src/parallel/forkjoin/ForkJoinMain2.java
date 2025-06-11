package parallel.forkjoin;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static util.MyLogger.log;

public class ForkJoinMain2 {

    public static void main(String[] args) {
        int processorCount = Runtime.getRuntime().availableProcessors();
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        log("processorCount = " + processorCount + ", commonPool = " + commonPool.getParallelism());

        List<Integer> data = IntStream.rangeClosed(1, 8)
                .boxed()
                .toList();

        log("[생성] " + data);

        long startTime = System.currentTimeMillis();

        SumTask task = new SumTask(data);
        Integer result = task.invoke(); // 공용 풀 사용

        long endTime = System.currentTimeMillis();
        log("time: " + (endTime - startTime) + "ms, sum: " + result);
    }

    /*
        23:44:15.720 [     main] processorCount = 8, commonPool = 7
        23:44:15.723 [     main] [생성] [1, 2, 3, 4, 5, 6, 7, 8]
        23:44:15.728 [     main] [분할] [1, 2, 3, 4, 5, 6, 7, 8] -> LEFT[1, 2, 3, 4], RIGHT[5, 6, 7, 8]
        23:44:15.728 [     main] [분할] [5, 6, 7, 8] -> LEFT[5, 6], RIGHT[7, 8]
        23:44:15.729 [ForkJoinPool.commonPool-worker-1] [분할] [1, 2, 3, 4] -> LEFT[1, 2], RIGHT[3, 4]
        23:44:15.729 [     main] [처리 시작] [7, 8]
        23:44:15.731 [ForkJoinPool.commonPool-worker-3] [처리 시작] [1, 2]
        23:44:15.733 [     main] calculate 7 -> 70
        23:44:15.752 [ForkJoinPool.commonPool-worker-3] calculate 1 -> 10
        23:44:15.729 [ForkJoinPool.commonPool-worker-2] [처리 시작] [5, 6]
        23:44:15.755 [ForkJoinPool.commonPool-worker-2] calculate 5 -> 50
        23:44:15.729 [ForkJoinPool.commonPool-worker-1] [처리 시작] [3, 4]
        23:44:15.763 [ForkJoinPool.commonPool-worker-1] calculate 3 -> 30
        23:44:16.744 [     main] calculate 8 -> 80
        23:44:16.757 [ForkJoinPool.commonPool-worker-2] calculate 6 -> 60
        23:44:16.757 [ForkJoinPool.commonPool-worker-3] calculate 2 -> 20
        23:44:16.766 [ForkJoinPool.commonPool-worker-1] calculate 4 -> 40
        23:44:17.755 [     main] [처리 완료] [7, 8] -> sum: 150
        23:44:17.762 [ForkJoinPool.commonPool-worker-3] [처리 완료] [1, 2] -> sum: 30
        23:44:17.763 [ForkJoinPool.commonPool-worker-2] [처리 완료] [5, 6] -> sum: 110
        23:44:17.768 [     main] LEFT[110] + RIGHT[150] -> sum: 260
        23:44:17.771 [ForkJoinPool.commonPool-worker-1] [처리 완료] [3, 4] -> sum: 70
        23:44:17.771 [ForkJoinPool.commonPool-worker-1] LEFT[30] + RIGHT[70] -> sum: 100
        23:44:17.771 [     main] LEFT[100] + RIGHT[260] -> sum: 360
        23:44:17.773 [     main] time: 2048ms, sum: 360
     */
}
