package thread.executor.future;

import thread.executor.CallableTask;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static util.MyLogger.log;

public class InvokeAllMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CallableTask task1 = new CallableTask("task1", 1000);
        CallableTask task2 = new CallableTask("task2", 2000);
        CallableTask task3 = new CallableTask("task3", 3000);

        List<CallableTask> tasks = List.of(task1, task2, task3);

        List<Future<Integer>> futures = es.invokeAll(tasks); // 모든 작업이 끝나야 응답

        for (Future<Integer> future : futures) {
            Integer value = future.get();
            log("value = " + value);
        }

        es.close();
    }

    /*
        2024-10-12 17:12:46.526 [pool-1-thread-1] task1 실행
        2024-10-12 17:12:46.526 [pool-1-thread-2] task2 실행
        2024-10-12 17:12:46.526 [pool-1-thread-3] task3 실행
        2024-10-12 17:12:47.530 [pool-1-thread-1] task1 완료
        2024-10-12 17:12:48.533 [pool-1-thread-2] task2 완료
        2024-10-12 17:12:49.533 [pool-1-thread-3] task3 완료
        2024-10-12 17:12:49.533 [     main] value = 1000
        2024-10-12 17:12:49.534 [     main] value = 2000
        2024-10-12 17:12:49.534 [     main] value = 3000
     */
}
