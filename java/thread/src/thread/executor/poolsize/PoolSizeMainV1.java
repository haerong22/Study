package thread.executor.poolsize;

import thread.executor.RunnableTask;

import java.util.concurrent.*;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class PoolSizeMainV1 {

    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        // queue 가 다차면 maximum 이 늘어남
        ThreadPoolExecutor es = new ThreadPoolExecutor(2, 4, 3000, TimeUnit.MILLISECONDS, workQueue);
        printState(es);

        es.execute(new RunnableTask("task1"));
        printState(es, "task1");

        es.execute(new RunnableTask("task2"));
        printState(es, "task2");

        es.execute(new RunnableTask("task3")); // 큐 저장
        printState(es, "task3");

        es.execute(new RunnableTask("task4")); // 큐 저장
        printState(es, "task4");

        es.execute(new RunnableTask("task5")); // poolSize 증가
        printState(es, "task5");

        es.execute(new RunnableTask("task6")); // poolSize 증가 (maximum)
        printState(es, "task6");

        try {
            es.execute(new RunnableTask("task7")); // 예외 발생
        } catch (RejectedExecutionException e) {
            log("task7 실행 거절 예외 발생: " + e);
        }

        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);

        sleep(3000);
        log("== maximumPoolSize 대기시간 초과 ==");
        printState(es); // coreSize 로 줄어듬

        es.close();
        log("== shutdown 완료 ==");
        printState(es);
    }

    /*
        2024-10-12 22:16:16.569 [     main] [pool=0, active=0, queuedTasks=0, completedTask=0]
        2024-10-12 22:16:16.592 [pool-1-thread-1] task1 시작
        2024-10-12 22:16:16.621 [     main] task1 -> [pool=1, active=1, queuedTasks=0, completedTask=0]
        2024-10-12 22:16:16.626 [     main] task2 -> [pool=2, active=2, queuedTasks=0, completedTask=0]
        2024-10-12 22:16:16.626 [     main] task3 -> [pool=2, active=2, queuedTasks=1, completedTask=0]
        2024-10-12 22:16:16.626 [     main] task4 -> [pool=2, active=2, queuedTasks=2, completedTask=0]
        2024-10-12 22:16:16.627 [pool-1-thread-2] task2 시작
        2024-10-12 22:16:16.633 [     main] task5 -> [pool=3, active=3, queuedTasks=2, completedTask=0]
        2024-10-12 22:16:16.634 [pool-1-thread-3] task5 시작
        2024-10-12 22:16:16.635 [     main] task6 -> [pool=4, active=4, queuedTasks=2, completedTask=0]
        2024-10-12 22:16:16.637 [     main] task7 실행 거절 예외 발생: java.util.concurrent.RejectedExecutionException: Task thread.executor.RunnableTask@7aec35a rejected from java.util.concurrent.ThreadPoolExecutor@12edcd21[Running, pool size = 4, active threads = 4, queued tasks = 2, completed tasks = 0]
        2024-10-12 22:16:16.639 [pool-1-thread-4] task6 시작
        2024-10-12 22:16:17.599 [pool-1-thread-1] task1 완료
        2024-10-12 22:16:17.601 [pool-1-thread-1] task3 시작
        2024-10-12 22:16:17.632 [pool-1-thread-2] task2 완료
        2024-10-12 22:16:17.632 [pool-1-thread-2] task4 시작
        2024-10-12 22:16:17.638 [pool-1-thread-3] task5 완료
        2024-10-12 22:16:17.645 [pool-1-thread-4] task6 완료
        2024-10-12 22:16:18.602 [pool-1-thread-1] task3 완료
        2024-10-12 22:16:18.633 [pool-1-thread-2] task4 완료
        2024-10-12 22:16:19.643 [     main] == 작업 수행 완료 ==
        2024-10-12 22:16:19.643 [     main] [pool=4, active=0, queuedTasks=0, completedTask=6]
        2024-10-12 22:16:22.645 [     main] == maximumPoolSize 대기시간 초과 ==
        2024-10-12 22:16:22.645 [     main] [pool=2, active=0, queuedTasks=0, completedTask=6]
        2024-10-12 22:16:22.646 [     main] == shutdown 완료 ==
        2024-10-12 22:16:22.646 [     main] [pool=0, active=0, queuedTasks=0, completedTask=6]
     */
}
