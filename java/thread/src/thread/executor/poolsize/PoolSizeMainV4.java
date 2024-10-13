package thread.executor.poolsize;

import thread.executor.RunnableTask;

import java.util.concurrent.*;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;

public class PoolSizeMainV4 {

//    private static final int TASK_SIZE = 1100; // 1. 일반
//    private static final int TASK_SIZE = 1200; // 2. 긴급
    private static final int TASK_SIZE = 1201; // 3. 거절

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(
                100, 200, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000)
        );

        printState(es);

        long startMs = System.currentTimeMillis();
        for (int i = 1; i <= TASK_SIZE; i++) {
            String taskName = "task" + i;
            try {
                es.execute(new RunnableTask(taskName));
                printState(es, taskName);
            } catch (RejectedExecutionException e) {
                log(taskName + " -> " + e);
            }
        }

        es.close();
        long endMs = System.currentTimeMillis();
        log("time: " + (endMs - startMs) + "ms");
    }

    /*
        1. TASK_SIZE = 1100

        ...

        2024-10-13 16:41:00.816 [     main] [pool=100, active=100, queuedTasks=1000, completedTask=0]

        ...

        2024-10-13 16:41:11.820 [pool-1-thread-91] task1099 완료
        2024-10-13 16:41:11.820 [pool-1-thread-71] task1100 완료
        2024-10-13 16:41:11.822 [     main] time: 11098ms

        2. TASK_SIZE = 1200

        ...

        2024-10-13 16:43:38.927 [     main] task1100 -> [pool=100, active=100, queuedTasks=1000, completedTask=0]

        ...

        2024-10-13 16:43:38.939 [     main] task1200 -> [pool=200, active=200, queuedTasks=1000, completedTask=0]

        ...

        024-10-13 16:43:44.965 [pool-1-thread-142] task1099 완료
        2024-10-13 16:43:44.965 [pool-1-thread-191] task1100 완료
        2024-10-13 16:43:44.966 [     main] time: 6107ms

        3. TASK_SIZE = 1201

        ...

        2024-10-13 16:45:16.587 [     main] task1200 -> [pool=200, active=200, queuedTasks=1000, completedTask=0]
        2024-10-13 16:45:16.587 [pool-1-thread-200] task1200 시작
        2024-10-13 16:45:16.590 [     main] task1201 -> java.util.concurrent.RejectedExecutionException: Task thread.executor.RunnableTask@6a6824be rejected from java.util.concurrent.ThreadPoolExecutor@12edcd21[Running, pool size = 200, active threads = 200, queued tasks = 1000, completed tasks = 0]

        ...

        2024-10-13 16:45:22.660 [pool-1-thread-103] task1099 완료
        2024-10-13 16:45:22.660 [pool-1-thread-191] task1100 완료
        2024-10-13 16:45:22.663 [     main] time: 6171ms
     */
}
