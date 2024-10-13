package thread.executor.poolsize;

import thread.executor.RunnableTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;

public class PoolSizeMainV2 {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2); // 큐 사이즈 제한 X

        log("pool 생성");
        printState(es);

        for (int i = 1; i <= 6; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            printState(es, taskName);
        }

        es.close();
        log("== shutdown 완료 ==");
    }

    /*
        2024-10-13 16:14:41.703 [     main] pool 생성
        2024-10-13 16:14:41.716 [     main] [pool=0, active=0, queuedTasks=0, completedTask=0]
        2024-10-13 16:14:41.719 [pool-1-thread-1] task1 시작
        2024-10-13 16:14:41.724 [     main] task1 -> [pool=1, active=1, queuedTasks=0, completedTask=0]
        2024-10-13 16:14:41.725 [     main] task2 -> [pool=2, active=2, queuedTasks=0, completedTask=0]
        2024-10-13 16:14:41.725 [pool-1-thread-2] task2 시작
        2024-10-13 16:14:41.726 [     main] task3 -> [pool=2, active=2, queuedTasks=1, completedTask=0]
        2024-10-13 16:14:41.726 [     main] task4 -> [pool=2, active=2, queuedTasks=2, completedTask=0]
        2024-10-13 16:14:41.726 [     main] task5 -> [pool=2, active=2, queuedTasks=3, completedTask=0]
        2024-10-13 16:14:41.726 [     main] task6 -> [pool=2, active=2, queuedTasks=4, completedTask=0]
        2024-10-13 16:14:42.724 [pool-1-thread-1] task1 완료
        2024-10-13 16:14:42.724 [pool-1-thread-1] task3 시작
        2024-10-13 16:14:42.728 [pool-1-thread-2] task2 완료
        2024-10-13 16:14:42.728 [pool-1-thread-2] task4 시작
        2024-10-13 16:14:43.728 [pool-1-thread-1] task3 완료
        2024-10-13 16:14:43.728 [pool-1-thread-1] task5 시작
        2024-10-13 16:14:43.733 [pool-1-thread-2] task4 완료
        2024-10-13 16:14:43.733 [pool-1-thread-2] task6 시작
        2024-10-13 16:14:44.734 [pool-1-thread-1] task5 완료
        2024-10-13 16:14:44.738 [pool-1-thread-2] task6 완료
        2024-10-13 16:14:44.739 [     main] == shutdown 완료 ==
     */
}
