package parallel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static util.MyLogger.log;

public class ParallelMain7 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService requestPool = Executors.newFixedThreadPool(100);

        // logic 처리 전용 쓰레드풀
        ExecutorService logicPool = Executors.newFixedThreadPool(400);

        int nThreads = 10;
        for (int i = 1; i <= nThreads; i++) {
            String requestName = "request" + i;
            requestPool.submit(() -> logic(requestName, logicPool));
            Thread.sleep(100);
        }
        requestPool.close();
        logicPool.close();
    }

    /*
        20:28:22.698 [pool-1-thread-1] [request1] START
        20:28:22.709 [pool-2-thread-2] [request1] 2 -> 20
        20:28:22.709 [pool-2-thread-3] [request1] 3 -> 30
        20:28:22.709 [pool-2-thread-1] [request1] 1 -> 10
        20:28:22.778 [pool-1-thread-2] [request2] START
        20:28:22.779 [pool-2-thread-4] [request2] 1 -> 10
        20:28:22.779 [pool-2-thread-5] [request2] 2 -> 20
        20:28:22.780 [pool-2-thread-6] [request2] 3 -> 30
        20:28:22.880 [pool-1-thread-3] [request3] START
        20:28:22.880 [pool-2-thread-7] [request3] 1 -> 10
        20:28:22.880 [pool-2-thread-8] [request3] 2 -> 20
        20:28:22.881 [pool-2-thread-9] [request3] 3 -> 30
        20:28:22.982 [pool-1-thread-4] [request4] START
        20:28:22.982 [pool-2-thread-10] [request4] 1 -> 10
        20:28:22.983 [pool-2-thread-11] [request4] 2 -> 20
        20:28:22.984 [pool-2-thread-12] [request4] 3 -> 30
        20:28:23.083 [pool-1-thread-5] [request5] START
        20:28:23.085 [pool-2-thread-13] [request5] 1 -> 10
        20:28:23.085 [pool-2-thread-14] [request5] 2 -> 20
        20:28:23.086 [pool-2-thread-15] [request5] 3 -> 30
        20:28:23.188 [pool-1-thread-6] [request6] START
        20:28:23.188 [pool-2-thread-17] [request6] 2 -> 20
        20:28:23.188 [pool-2-thread-16] [request6] 1 -> 10
        20:28:23.188 [pool-2-thread-18] [request6] 3 -> 30
        20:28:23.290 [pool-1-thread-7] [request7] START
        20:28:23.290 [pool-2-thread-19] [request7] 1 -> 10
        20:28:23.290 [pool-2-thread-20] [request7] 2 -> 20
        20:28:23.291 [pool-2-thread-21] [request7] 3 -> 30
        20:28:23.395 [pool-1-thread-8] [request8] START
        20:28:23.395 [pool-2-thread-22] [request8] 1 -> 10
        20:28:23.396 [pool-2-thread-23] [request8] 2 -> 20
        20:28:23.396 [pool-2-thread-24] [request8] 3 -> 30
        20:28:23.498 [pool-1-thread-9] [request9] START
        20:28:23.499 [pool-2-thread-25] [request9] 1 -> 10
        20:28:23.499 [pool-2-thread-27] [request9] 3 -> 30
        20:28:23.499 [pool-2-thread-26] [request9] 2 -> 20
        20:28:23.603 [pool-1-thread-10] [request10] START
        20:28:23.604 [pool-2-thread-28] [request10] 1 -> 10
        20:28:23.604 [pool-2-thread-30] [request10] 3 -> 30
        20:28:23.604 [pool-2-thread-29] [request10] 2 -> 20
        20:28:23.722 [pool-1-thread-1] [request1] time: 1015ms, sum: 60
        20:28:23.785 [pool-1-thread-2] [request2] time: 1007ms, sum: 60
        20:28:23.893 [pool-1-thread-3] [request3] time: 1013ms, sum: 60
        20:28:23.986 [pool-1-thread-4] [request4] time: 1004ms, sum: 60
        20:28:24.092 [pool-1-thread-5] [request5] time: 1009ms, sum: 60
        20:28:24.193 [pool-1-thread-6] [request6] time: 1005ms, sum: 60
        20:28:24.296 [pool-1-thread-7] [request7] time: 1006ms, sum: 60
        20:28:24.401 [pool-1-thread-8] [request8] time: 1006ms, sum: 60
        20:28:24.504 [pool-1-thread-9] [request9] time: 1005ms, sum: 60
        20:28:24.609 [pool-1-thread-10] [request10] time: 1005ms, sum: 60
     */

    private static void logic(String requestName, ExecutorService es) {
        log("[" + requestName + "] START");
        long startTime = System.currentTimeMillis();

        List<Future<Integer>> futures = IntStream.range(1, 4)
                .mapToObj(i -> es.submit(() -> HeavyJob.heavyTask(i, requestName)))
                .toList();

        int sum = futures.stream()
                .mapToInt(f -> {
                    try {
                        return f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();

        long endTime = System.currentTimeMillis();
        log("[" + requestName + "] time: " + (endTime - startTime) + "ms, sum: " + sum);
    }
}
