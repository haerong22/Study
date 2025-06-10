package parallel.forkjoin;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static util.MyLogger.log;

public class ForkJoinMain1 {

    public static void main(String[] args) {
        List<Integer> data = IntStream.rangeClosed(1, 8)
                .boxed()
                .toList();

        log("[생성] " + data);

        // ForkJoinPool
        long startTime = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool(10);
        SumTask task = new SumTask(data);

        Integer result = pool.invoke(task);
        pool.close();

        long endTime = System.currentTimeMillis();
        log("time: " + (endTime - startTime) + "ms, sum: " + result);
        log("pool: " + pool);
    }

    /*
        23:28:26.638 [     main] [생성] [1, 2, 3, 4, 5, 6, 7, 8]
        23:28:26.657 [ForkJoinPool-1-worker-1] [분할] [1, 2, 3, 4, 5, 6, 7, 8] -> LEFT[1, 2, 3, 4], RIGHT[5, 6, 7, 8]
        23:28:26.657 [ForkJoinPool-1-worker-1] [처리 시작] [5, 6, 7, 8]
        23:28:26.657 [ForkJoinPool-1-worker-2] [처리 시작] [1, 2, 3, 4]
        23:28:26.662 [ForkJoinPool-1-worker-1] calculate 5 -> 50
        23:28:26.662 [ForkJoinPool-1-worker-2] calculate 1 -> 10
        23:28:27.667 [ForkJoinPool-1-worker-2] calculate 2 -> 20
        23:28:27.667 [ForkJoinPool-1-worker-1] calculate 6 -> 60
        23:28:28.669 [ForkJoinPool-1-worker-1] calculate 7 -> 70
        23:28:28.673 [ForkJoinPool-1-worker-2] calculate 3 -> 30
        23:28:29.675 [ForkJoinPool-1-worker-2] calculate 4 -> 40
        23:28:29.676 [ForkJoinPool-1-worker-1] calculate 8 -> 80
        23:28:30.691 [ForkJoinPool-1-worker-1] [처리 완료] [5, 6, 7, 8] -> sum: 260
        23:28:30.691 [ForkJoinPool-1-worker-2] [처리 완료] [1, 2, 3, 4] -> sum: 100
        23:28:30.698 [ForkJoinPool-1-worker-1] LEFT[100] + RIGHT[260] -> sum: 360
        23:28:30.702 [     main] time: 4048ms, sum: 360
        23:28:30.703 [     main] pool: java.util.concurrent.ForkJoinPool@1d56ce6a[Terminated, parallelism = 10, size = 0, active = 0, running = 0, steals = 2, tasks = 0, submissions = 0]
     */
}
