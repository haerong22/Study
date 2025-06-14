package parallel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static util.MyLogger.log;

public class ParallelMain6 {

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
        20:19:28.826 [pool-1-thread-1] [request1] START
        20:19:28.834 [pool-2-thread-4] [request1] 4 -> 40
        20:19:28.834 [pool-2-thread-3] [request1] 3 -> 30
        20:19:28.834 [pool-2-thread-1] [request1] 1 -> 10
        20:19:28.834 [pool-2-thread-2] [request1] 2 -> 20
        20:19:28.918 [pool-1-thread-2] [request2] START
        20:19:28.918 [pool-2-thread-5] [request2] 1 -> 10
        20:19:28.918 [pool-2-thread-6] [request2] 2 -> 20
        20:19:28.918 [pool-2-thread-7] [request2] 3 -> 30
        20:19:28.918 [pool-2-thread-8] [request2] 4 -> 40
        20:19:29.021 [pool-1-thread-3] [request3] START
        20:19:29.021 [pool-2-thread-9] [request3] 1 -> 10
        20:19:29.021 [pool-2-thread-10] [request3] 2 -> 20
        20:19:29.022 [pool-2-thread-11] [request3] 3 -> 30
        20:19:29.022 [pool-2-thread-12] [request3] 4 -> 40
        20:19:29.125 [pool-1-thread-4] [request4] START
        20:19:29.126 [pool-2-thread-13] [request4] 1 -> 10
        20:19:29.126 [pool-2-thread-14] [request4] 2 -> 20
        20:19:29.126 [pool-2-thread-15] [request4] 3 -> 30
        20:19:29.126 [pool-2-thread-16] [request4] 4 -> 40
        20:19:29.228 [pool-1-thread-5] [request5] START
        20:19:29.228 [pool-2-thread-17] [request5] 1 -> 10
        20:19:29.229 [pool-2-thread-18] [request5] 2 -> 20
        20:19:29.229 [pool-2-thread-19] [request5] 3 -> 30
        20:19:29.229 [pool-2-thread-20] [request5] 4 -> 40
        20:19:29.334 [pool-1-thread-6] [request6] START
        20:19:29.334 [pool-2-thread-21] [request6] 1 -> 10
        20:19:29.334 [pool-2-thread-22] [request6] 2 -> 20
        20:19:29.334 [pool-2-thread-23] [request6] 3 -> 30
        20:19:29.334 [pool-2-thread-24] [request6] 4 -> 40
        20:19:29.439 [pool-1-thread-7] [request7] START
        20:19:29.440 [pool-2-thread-25] [request7] 1 -> 10
        20:19:29.440 [pool-2-thread-26] [request7] 2 -> 20
        20:19:29.440 [pool-2-thread-27] [request7] 3 -> 30
        20:19:29.440 [pool-2-thread-28] [request7] 4 -> 40
        20:19:29.545 [pool-1-thread-8] [request8] START
        20:19:29.545 [pool-2-thread-29] [request8] 1 -> 10
        20:19:29.545 [pool-2-thread-30] [request8] 2 -> 20
        20:19:29.545 [pool-2-thread-31] [request8] 3 -> 30
        20:19:29.545 [pool-2-thread-32] [request8] 4 -> 40
        20:19:29.646 [pool-1-thread-9] [request9] START
        20:19:29.647 [pool-2-thread-33] [request9] 1 -> 10
        20:19:29.647 [pool-2-thread-34] [request9] 2 -> 20
        20:19:29.647 [pool-2-thread-36] [request9] 4 -> 40
        20:19:29.647 [pool-2-thread-35] [request9] 3 -> 30
        20:19:29.752 [pool-1-thread-10] [request10] START
        20:19:29.752 [pool-2-thread-37] [request10] 1 -> 10
        20:19:29.752 [pool-2-thread-38] [request10] 2 -> 20
        20:19:29.752 [pool-2-thread-39] [request10] 3 -> 30
        20:19:29.752 [pool-2-thread-40] [request10] 4 -> 40
        20:19:29.843 [pool-1-thread-1] [request1] time: 1012ms, sum: 100
        20:19:29.923 [pool-1-thread-2] [request2] time: 1005ms, sum: 100
        20:19:30.027 [pool-1-thread-3] [request3] time: 1006ms, sum: 100
        20:19:30.132 [pool-1-thread-4] [request4] time: 1005ms, sum: 100
        20:19:30.234 [pool-1-thread-5] [request5] time: 1006ms, sum: 100
        20:19:30.338 [pool-1-thread-6] [request6] time: 1004ms, sum: 100
        20:19:30.447 [pool-1-thread-7] [request7] time: 1006ms, sum: 100
        20:19:30.552 [pool-1-thread-8] [request8] time: 1006ms, sum: 100
        20:19:30.652 [pool-1-thread-9] [request9] time: 1005ms, sum: 100
        20:19:30.758 [pool-1-thread-10] [request10] time: 1006ms, sum: 100
     */

    private static void logic(String requestName, ExecutorService es) {
        log("[" + requestName + "] START");
        long startTime = System.currentTimeMillis();

        Future<Integer> f1 = es.submit(() -> HeavyJob.heavyTask(1, requestName));
        Future<Integer> f2 = es.submit(() -> HeavyJob.heavyTask(2, requestName));
        Future<Integer> f3 = es.submit(() -> HeavyJob.heavyTask(3, requestName));
        Future<Integer> f4 = es.submit(() -> HeavyJob.heavyTask(4, requestName));
        
        int sum;

        try {
            Integer v1 = f1.get();
            Integer v2 = f2.get();
            Integer v3 = f3.get();
            Integer v4 = f4.get();
            sum = v1 + v2 + v3 + v4;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        log("[" + requestName + "] time: " + (endTime - startTime) + "ms, sum: " + sum);
    }
}
