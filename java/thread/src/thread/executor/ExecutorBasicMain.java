package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static thread.executor.ExecutorUtils.*;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ExecutorBasicMain {

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        log("== 초기 상태 ==");
        printState(es);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("taskD"));
        log("== 작업 수행 중 ==");
        printState(es);

        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);

        es.close();
        log("== shutdown 완료 ==");
        printState(es);
    }

    /*
        2024-10-10 23:30:54.548 [     main] == 초기 상태 ==
        2024-10-10 23:30:54.559 [     main] [pool=0, active=0, queuedTasks=0, completedTask=0]
        2024-10-10 23:30:54.562 [     main] == 작업 수행 중 ==
        2024-10-10 23:30:54.562 [     main] [pool=2, active=2, queuedTasks=2, completedTask=0]
        2024-10-10 23:30:54.562 [pool-1-thread-1] taskA 시작
        2024-10-10 23:30:54.563 [pool-1-thread-2] taskB 시작
        2024-10-10 23:30:55.566 [pool-1-thread-2] taskB 완료
        2024-10-10 23:30:55.567 [pool-1-thread-2] taskC 시작
        2024-10-10 23:30:55.568 [pool-1-thread-1] taskA 완료
        2024-10-10 23:30:55.568 [pool-1-thread-1] taskD 시작
        2024-10-10 23:30:56.571 [pool-1-thread-2] taskC 완료
        2024-10-10 23:30:56.572 [pool-1-thread-1] taskD 완료
        2024-10-10 23:30:57.568 [     main] == 작업 수행 완료 ==
        2024-10-10 23:30:57.568 [     main] [pool=2, active=0, queuedTasks=0, completedTask=4]
        2024-10-10 23:30:57.569 [     main] == shutdown 완료 ==
        2024-10-10 23:30:57.569 [     main] [pool=0, active=0, queuedTasks=0, completedTask=4]
     */
}
