package thread.executor.reject;

import thread.executor.RunnableTask;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static util.MyLogger.log;

/**
 * AbortPolicy (기본전략)
 * - 요청 초과시 RejectedExecutionException 발생
 */
public class RejectMainV1 {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.AbortPolicy()
        );

        executor.submit(new RunnableTask("task1"));

        try {
            executor.submit(new RunnableTask("task2"));
        } catch (RejectedExecutionException e) {
            log("요청 초과");
            log(e);
        }

        executor.close();
    }

    /*
        2024-10-13 16:55:36.834 [pool-1-thread-1] task1 시작
        2024-10-13 16:55:36.834 [     main] 요청 초과
        2024-10-13 16:55:36.835 [     main] java.util.concurrent.RejectedExecutionException: Task java.util.concurrent.FutureTask@b1bc7ed[Not completed, task = java.util.concurrent.Executors$RunnableAdapter@1ddc4ec2[Wrapped task = thread.executor.RunnableTask@133314b]] rejected from java.util.concurrent.ThreadPoolExecutor@5b6f7412[Running, pool size = 1, active threads = 1, queued tasks = 0, completed tasks = 0]
        2024-10-13 16:55:37.839 [pool-1-thread-1] task1 완료
     */
}
