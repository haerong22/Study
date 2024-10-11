package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;

public class SumTaskMainV2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);

        ExecutorService es = Executors.newFixedThreadPool(2);

        Future<Integer> future1 = es.submit(task1);
        Future<Integer> future2 = es.submit(task2);

        Integer sum1 = future1.get();
        Integer sum2 = future2.get();

        log("task1.result = " + sum1);
        log("task2.result = " + sum2);

        int sumAll = sum2 + sum2;
        log("task1 + task2 = " + sumAll);
        log("end");

        es.close();
    }

    /*
        2024-10-11 21:51:32.969 [pool-1-thread-1] 작업 시작
        2024-10-11 21:51:32.969 [pool-1-thread-2] 작업 시작`
        2024-10-11 21:51:34.982 [pool-1-thread-2] 작업 완료 result = 3775
        2024-10-11 21:51:34.982 [pool-1-thread-1] 작업 완료 result = 1275
        2024-10-11 21:51:34.983 [     main] task1.result = 1275
        2024-10-11 21:51:34.983 [     main] task2.result = 3775
        2024-10-11 21:51:34.983 [     main] task1 + task2 = 7550
        2024-10-11 21:51:34.983 [     main] end
     */

    static class SumTask implements Callable<Integer> {

        int startValue;
        int endValue;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public Integer call() throws Exception {
            log("작업 시작");
            Thread.sleep(2000);
            int sum = 0;
            for (int i = startValue; i <= endValue ; i++) {
                sum += i;
            }
            log("작업 완료 result = " + sum);
            return sum;
        }
    }
}
