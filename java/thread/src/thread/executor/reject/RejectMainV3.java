package thread.executor.reject;

import thread.executor.RunnableTask;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * CallerRunsPolicy
 * - 요청 초과시 요청한 쓰레드가 실행
 */
public class RejectMainV3 {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy()
        );

        executor.submit(new RunnableTask("task1"));
        executor.submit(new RunnableTask("task2"));
        executor.submit(new RunnableTask("task3"));
        executor.submit(new RunnableTask("task4"));

        executor.close();
    }

    /*
        2024-10-13 16:58:48.990 [pool-1-thread-1] task1 시작
        2024-10-13 16:58:48.991 [     main] task2 시작
        2024-10-13 16:58:49.997 [     main] task2 완료
        2024-10-13 16:58:49.998 [pool-1-thread-1] task1 완료
        2024-10-13 16:58:49.998 [     main] task3 시작
        2024-10-13 16:58:51.003 [     main] task3 완료
        2024-10-13 16:58:51.004 [pool-1-thread-1] task4 시작
        2024-10-13 16:58:52.008 [pool-1-thread-1] task4 완료
     */
}
