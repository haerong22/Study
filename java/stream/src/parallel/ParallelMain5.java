package parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static util.MyLogger.log;

public class ParallelMain5 {

    /**
     * 병렬 스트림 사용시 주의
     * - 공용 풀은 CPU bound 작업에만 사용, I/O bound 작업엔 X
     *
     * 공용 풀 병목: 모든 병렬 스트림이 동일한 공용 풀 공유
     * 자원 경쟁: 여러 요청이 제한된 쓰레드 풀을 두고 경쟁
     * 예측 불가능한 성능: 같은 작업이라도 동시에 실행되는 다른 작업의 수에 따라 처리시간이 다름
     */
    public static void main(String[] args) throws InterruptedException {
        // 병렬 수준 3으로 제한
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "3");

        ExecutorService requestPool = Executors.newFixedThreadPool(100);
        int nThreads = 10;
        for (int i = 1; i <= nThreads; i++) {
            String requestName = "request" + i;
            requestPool.submit(() -> logic(requestName));
            Thread.sleep(100);
        }
        requestPool.close();
    }

    /*
        ** Parallel **
        [Threads = 1]
        23:18:24.453 [pool-1-thread-1] [request1] START
        23:18:24.462 [pool-1-thread-1] [request1] 3 -> 30
        23:18:24.462 [ForkJoinPool.commonPool-worker-2] [request1] 4 -> 40
        23:18:24.462 [ForkJoinPool.commonPool-worker-3] [request1] 1 -> 10
        23:18:24.462 [ForkJoinPool.commonPool-worker-1] [request1] 2 -> 20
        23:18:25.472 [pool-1-thread-1] [request1] time: 1012ms, sum: 100

        [Threads = 10]
        23:19:40.216 [pool-1-thread-1] [request1] START
        23:19:40.223 [ForkJoinPool.commonPool-worker-3] [request1] 1 -> 10
        23:19:40.223 [pool-1-thread-1] [request1] 3 -> 30
        23:19:40.223 [ForkJoinPool.commonPool-worker-2] [request1] 4 -> 40
        23:19:40.223 [ForkJoinPool.commonPool-worker-1] [request1] 2 -> 20
        23:19:40.304 [pool-1-thread-2] [request2] START
        23:19:40.305 [pool-1-thread-2] [request2] 3 -> 30
        23:19:40.405 [pool-1-thread-3] [request3] START
        23:19:40.406 [pool-1-thread-3] [request3] 3 -> 30
        23:19:40.511 [pool-1-thread-4] [request4] START
        23:19:40.511 [pool-1-thread-4] [request4] 3 -> 30
        23:19:40.616 [pool-1-thread-5] [request5] START
        23:19:40.616 [pool-1-thread-5] [request5] 3 -> 30
        23:19:40.719 [pool-1-thread-6] [request6] START
        23:19:40.719 [pool-1-thread-6] [request6] 3 -> 30
        23:19:40.823 [pool-1-thread-7] [request7] START
        23:19:40.823 [pool-1-thread-7] [request7] 3 -> 30
        23:19:40.926 [pool-1-thread-8] [request8] START
        23:19:40.926 [pool-1-thread-8] [request8] 3 -> 30
        23:19:41.028 [pool-1-thread-9] [request9] START
        23:19:41.029 [pool-1-thread-9] [request9] 3 -> 30
        23:19:41.135 [pool-1-thread-10] [request10] START
        23:19:41.135 [pool-1-thread-10] [request10] 3 -> 30
        23:19:41.224 [ForkJoinPool.commonPool-worker-2] [request2] 2 -> 20
        23:19:41.227 [ForkJoinPool.commonPool-worker-1] [request2] 4 -> 40
        23:19:41.227 [ForkJoinPool.commonPool-worker-3] [request6] 2 -> 20
        23:19:41.238 [pool-1-thread-1] [request1] time: 1011ms, sum: 100
        23:19:41.310 [pool-1-thread-2] [request2] 1 -> 10
        23:19:41.411 [pool-1-thread-3] [request3] 2 -> 20
        23:19:41.516 [pool-1-thread-4] [request4] 2 -> 20
        23:19:41.616 [pool-1-thread-5] [request5] 2 -> 20
        23:19:41.724 [pool-1-thread-6] [request6] 4 -> 40
        23:19:42.135 [pool-1-thread-10] [request10] 4 -> 40
        23:19:42.227 [ForkJoinPool.commonPool-worker-1] [request10] 2 -> 20
        23:19:42.228 [ForkJoinPool.commonPool-worker-3] [request6] 1 -> 10
        23:19:42.229 [ForkJoinPool.commonPool-worker-2] [request10] 1 -> 10
        23:19:42.312 [pool-1-thread-2] [request2] time: 2007ms, sum: 100
        23:19:42.416 [pool-1-thread-3] [request3] 1 -> 10
        23:19:42.522 [pool-1-thread-4] [request4] 1 -> 10
        23:19:42.622 [pool-1-thread-5] [request5] 1 -> 10
        23:19:43.231 [ForkJoinPool.commonPool-worker-3] [request3] 4 -> 40
        23:19:43.232 [pool-1-thread-6] [request6] time: 2513ms, sum: 100
        23:19:43.231 [ForkJoinPool.commonPool-worker-1] [request7] 2 -> 20
        23:19:43.235 [ForkJoinPool.commonPool-worker-2] [request7] 1 -> 10
        23:19:43.235 [pool-1-thread-10] [request10] time: 2100ms, sum: 100
        23:19:43.527 [pool-1-thread-4] [request4] 4 -> 40
        23:19:43.628 [pool-1-thread-5] [request5] 4 -> 40
        23:19:44.235 [ForkJoinPool.commonPool-worker-3] [request7] 4 -> 40
        23:19:44.235 [pool-1-thread-3] [request3] time: 3829ms, sum: 100
        23:19:44.239 [ForkJoinPool.commonPool-worker-1] [request8] 2 -> 20
        23:19:44.240 [ForkJoinPool.commonPool-worker-2] [request8] 1 -> 10
        23:19:44.527 [pool-1-thread-4] [request4] time: 4016ms, sum: 100
        23:19:44.633 [pool-1-thread-5] [request5] time: 4017ms, sum: 100
        23:19:45.239 [ForkJoinPool.commonPool-worker-3] [request8] 4 -> 40
        23:19:45.239 [pool-1-thread-7] [request7] time: 4416ms, sum: 100
        23:19:45.243 [ForkJoinPool.commonPool-worker-2] [request9] 2 -> 20
        23:19:45.243 [ForkJoinPool.commonPool-worker-1] [request9] 4 -> 40
        23:19:46.244 [ForkJoinPool.commonPool-worker-3] [request9] 1 -> 10
        23:19:46.244 [pool-1-thread-8] [request8] time: 5318ms, sum: 100
        23:19:47.249 [pool-1-thread-9] [request9] time: 6220ms, sum: 100

        ** Single **
        [Threads = 10]
        23:29:15.054 [pool-1-thread-1] [request1] START
        23:29:15.060 [pool-1-thread-1] [request1] 1 -> 10
        23:29:15.142 [pool-1-thread-2] [request2] START
        23:29:15.142 [pool-1-thread-2] [request2] 1 -> 10
        23:29:15.251 [pool-1-thread-3] [request3] START
        23:29:15.251 [pool-1-thread-3] [request3] 1 -> 10
        23:29:15.356 [pool-1-thread-4] [request4] START
        23:29:15.356 [pool-1-thread-4] [request4] 1 -> 10
        23:29:15.522 [pool-1-thread-5] [request5] START
        23:29:15.522 [pool-1-thread-5] [request5] 1 -> 10
        23:29:15.623 [pool-1-thread-6] [request6] START
        23:29:15.623 [pool-1-thread-6] [request6] 1 -> 10
        23:29:15.725 [pool-1-thread-7] [request7] START
        23:29:15.725 [pool-1-thread-7] [request7] 1 -> 10
        23:29:15.830 [pool-1-thread-8] [request8] START
        23:29:15.831 [pool-1-thread-8] [request8] 1 -> 10
        23:29:15.935 [pool-1-thread-9] [request9] START
        23:29:15.935 [pool-1-thread-9] [request9] 1 -> 10
        23:29:16.039 [pool-1-thread-10] [request10] START
        23:29:16.040 [pool-1-thread-10] [request10] 1 -> 10
        23:29:16.066 [pool-1-thread-1] [request1] 2 -> 20
        23:29:16.144 [pool-1-thread-2] [request2] 2 -> 20
        23:29:16.253 [pool-1-thread-3] [request3] 2 -> 20
        23:29:16.361 [pool-1-thread-4] [request4] 2 -> 20
        23:29:16.527 [pool-1-thread-5] [request5] 2 -> 20
        23:29:16.625 [pool-1-thread-6] [request6] 2 -> 20
        23:29:16.731 [pool-1-thread-7] [request7] 2 -> 20
        23:29:16.834 [pool-1-thread-8] [request8] 2 -> 20
        23:29:16.941 [pool-1-thread-9] [request9] 2 -> 20
        23:29:17.044 [pool-1-thread-10] [request10] 2 -> 20
        23:29:17.067 [pool-1-thread-1] [request1] 3 -> 30
        23:29:17.150 [pool-1-thread-2] [request2] 3 -> 30
        23:29:17.257 [pool-1-thread-3] [request3] 3 -> 30
        23:29:17.363 [pool-1-thread-4] [request4] 3 -> 30
        23:29:17.528 [pool-1-thread-5] [request5] 3 -> 30
        23:29:17.631 [pool-1-thread-6] [request6] 3 -> 30
        23:29:17.733 [pool-1-thread-7] [request7] 3 -> 30
        23:29:17.839 [pool-1-thread-8] [request8] 3 -> 30
        23:29:17.949 [pool-1-thread-9] [request9] 3 -> 30
        23:29:18.047 [pool-1-thread-10] [request10] 3 -> 30
        23:29:18.069 [pool-1-thread-1] [request1] 4 -> 40
        23:29:18.153 [pool-1-thread-2] [request2] 4 -> 40
        23:29:18.261 [pool-1-thread-3] [request3] 4 -> 40
        23:29:18.369 [pool-1-thread-4] [request4] 4 -> 40
        23:29:18.532 [pool-1-thread-5] [request5] 4 -> 40
        23:29:18.639 [pool-1-thread-6] [request6] 4 -> 40
        23:29:18.740 [pool-1-thread-7] [request7] 4 -> 40
        23:29:18.845 [pool-1-thread-8] [request8] 4 -> 40
        23:29:18.954 [pool-1-thread-9] [request9] 4 -> 40
        23:29:19.051 [pool-1-thread-10] [request10] 4 -> 40
        23:29:19.077 [pool-1-thread-1] [request1] time: 4018ms, sum: 100
        23:29:19.156 [pool-1-thread-2] [request2] time: 4014ms, sum: 100
        23:29:19.267 [pool-1-thread-3] [request3] time: 4015ms, sum: 100
        23:29:19.374 [pool-1-thread-4] [request4] time: 4018ms, sum: 100
        23:29:19.538 [pool-1-thread-5] [request5] time: 4016ms, sum: 100
        23:29:19.643 [pool-1-thread-6] [request6] time: 4020ms, sum: 100
        23:29:19.743 [pool-1-thread-7] [request7] time: 4018ms, sum: 100
        23:29:19.850 [pool-1-thread-8] [request8] time: 4019ms, sum: 100
        23:29:19.960 [pool-1-thread-9] [request9] time: 4025ms, sum: 100
        23:29:20.056 [pool-1-thread-10] [request10] time: 4016ms, sum: 100
     */

    private static void logic(String requestName) {
        log("[" + requestName + "] START");
        long startTime = System.currentTimeMillis();

        int sum = IntStream.rangeClosed(1, 4)
//                .parallel()
                .map(i -> HeavyJob.heavyTask(i, requestName))
                .reduce(0, Integer::sum);

        long endTime = System.currentTimeMillis();
        log("[" + requestName + "] time: " + (endTime - startTime) + "ms, sum: " + sum);
    }
}
