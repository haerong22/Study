package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static thread.executor.ExecutorUtils.*;
import static util.MyLogger.log;

public class ExecutorShutdownMain {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("longTask", 100_000));
        printState(es);
        log("== shutdown 시작");
        shutdownAndAwaitTermination(es);
        log("== shutdown 완료");
        printState(es);
    }

    /*
        2024-10-12 22:02:39.799 [pool-1-thread-1] taskA 시작
        2024-10-12 22:02:39.799 [pool-1-thread-2] taskB 시작
        2024-10-12 22:02:39.799 [     main] [pool=2, active=2, queuedTasks=2, completedTask=0]
        2024-10-12 22:02:39.800 [     main] == shutdown 시작
        2024-10-12 22:02:40.803 [pool-1-thread-1] taskA 완료
        2024-10-12 22:02:40.803 [pool-1-thread-2] taskB 완료
        2024-10-12 22:02:40.803 [pool-1-thread-2] taskC 시작
        2024-10-12 22:02:40.803 [pool-1-thread-1] longTask 시작
        2024-10-12 22:02:41.808 [pool-1-thread-2] taskC 완료
        2024-10-12 22:02:49.806 [     main] 서비스 정상 종료 실패 -> 강제 종료 시도
        2024-10-12 22:02:49.808 [pool-1-thread-1] 인터럽트 발생, sleep interrupted
        2024-10-12 22:02:49.809 [     main] == shutdown 완료
        2024-10-12 22:02:49.809 [     main] [pool=0, active=0, queuedTasks=0, completedTask=4]
        Exception in thread "pool-1-thread-1" java.lang.RuntimeException: java.lang.InterruptedException: sleep interrupted
     */

    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown(); // non-blocking, 새로운 작업을 받지 않음, 처리중이거나 큐에 이미 대기중인 작업은 처리, 이후에 풀의 쓰레드 종료
        try {
            // 이미 대기중인 작업들을 모두 완료할 때까지 10초 대기
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                log("서비스 정상 종료 실패 -> 강제 종료 시도");
                es.shutdownNow();
                if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                    log("서비스가 종료되지 않았습니다.");
                }
            }
        } catch (InterruptedException e) {
            // awaitTermination() 으로 대기중인 현재 쓰레드가 인터럽트 될 수 있다.
            es.shutdownNow();
        }

    }
}
