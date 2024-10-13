package thread.executor.poolsize;

import thread.executor.RunnableTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;

/**
 * 캐시 풀
 * - 기본 쓰레드 사용 X, 60초 생존 주기를 가진 초과 쓰레드만 사용
 * - 초과 쓰레드의 제한 X
 * - 큐에 작업 저장 X (SynchronousQueue)
 * <p>
 * new ThreadPoolExecutor(0, Integer.MAX_VALUE,
 *                      60L, TimeUnit.SECONDS,
 *                      new SynchronousQueue<Runnable>())
 */
public class PoolSizeMainV3 {

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();

        log("pool 생성");
        printState(es);

        for (int i = 1; i <= 4; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            printState(es, taskName);
        }

        es.close();
        log("== shutdown 완료 ==");
    }

    /*
        2024-10-13 16:23:13.732 [     main] pool 생성
        2024-10-13 16:23:13.742 [     main] [pool=0, active=0, queuedTasks=0, completedTask=0]
        2024-10-13 16:23:13.745 [pool-1-thread-1] task1 시작
        2024-10-13 16:23:13.750 [     main] task1 -> [pool=1, active=1, queuedTasks=0, completedTask=0]
        2024-10-13 16:23:13.750 [     main] task2 -> [pool=2, active=2, queuedTasks=0, completedTask=0]
        2024-10-13 16:23:13.750 [pool-1-thread-2] task2 시작
        2024-10-13 16:23:13.750 [     main] task3 -> [pool=3, active=3, queuedTasks=0, completedTask=0]
        2024-10-13 16:23:13.750 [pool-1-thread-3] task3 시작
        2024-10-13 16:23:13.751 [     main] task4 -> [pool=4, active=4, queuedTasks=0, completedTask=0]
        2024-10-13 16:23:13.751 [pool-1-thread-4] task4 시작
        2024-10-13 16:23:14.751 [pool-1-thread-1] task1 완료
        2024-10-13 16:23:14.752 [pool-1-thread-2] task2 완료
        2024-10-13 16:23:14.754 [pool-1-thread-3] task3 완료
        2024-10-13 16:23:14.754 [pool-1-thread-4] task4 완료
        2024-10-13 16:23:14.755 [     main] == shutdown 완료 ==
     */
}
