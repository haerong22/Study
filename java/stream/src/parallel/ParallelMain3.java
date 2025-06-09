package parallel;

import java.util.concurrent.*;

import static util.MyLogger.log;

public class ParallelMain3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService es = Executors.newFixedThreadPool(2);

        long startTime = System.currentTimeMillis();

        SumTask task1 = new SumTask(1, 4);
        SumTask task2 = new SumTask(5, 8);

        Future<Integer> future1 = es.submit(task1);
        Future<Integer> future2 = es.submit(task2);

        Integer result1 = future1.get();
        Integer result2 = future2.get();
        log("main 쓰레드 대기 완료");

        int sum = result1 + result2;

        long endTime = System.currentTimeMillis();
        log("time: " + (endTime - startTime) + "ms, sum: " + sum);

        es.close();
    }

    /*
        23:06:56.517 [pool-1-thread-2] 작업 시작
        23:06:56.517 [pool-1-thread-1] 작업 시작
        23:06:56.523 [pool-1-thread-2] calculate 5 -> 50
        23:06:56.523 [pool-1-thread-1] calculate 1 -> 10
        23:06:57.526 [pool-1-thread-1] calculate 2 -> 20
        23:06:57.528 [pool-1-thread-2] calculate 6 -> 60
        23:06:58.531 [pool-1-thread-1] calculate 3 -> 30
        23:06:58.533 [pool-1-thread-2] calculate 7 -> 70
        23:06:59.538 [pool-1-thread-1] calculate 4 -> 40
        23:06:59.538 [pool-1-thread-2] calculate 8 -> 80
        23:07:00.547 [pool-1-thread-2] 작업 완료 result = 260
        23:07:00.547 [pool-1-thread-1] 작업 완료 result = 100
        23:07:00.547 [     main] main 쓰레드 대기 완료
        23:07:00.552 [     main] time: 4045ms, sum: 360
     */

    static class SumTask implements Callable<Integer> {

        int startValue;
        int endValue;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public Integer call() {
            log("작업 시작");

            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                int calculated = HeavyJob.heavyTask(i);
                sum += calculated;
            }

            log("작업 완료 result = " + sum);
            return sum;
        }
    }
}
