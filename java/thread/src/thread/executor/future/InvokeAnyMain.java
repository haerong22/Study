package thread.executor.future;

import thread.executor.CallableTask;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.MyLogger.log;

public class InvokeAnyMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CallableTask task1 = new CallableTask("task1", 1000);
        CallableTask task2 = new CallableTask("task2", 2000);
        CallableTask task3 = new CallableTask("task3", 3000);

        List<CallableTask> tasks = List.of(task1, task2, task3);

        Integer value = es.invokeAny(tasks); // 작업 하나가 끝나면 응답하고 나머지 취소
        log("value = " + value);

        es.close();
    }

    /*
        2024-10-12 17:14:13.825 [pool-1-thread-1] task1 실행
        2024-10-12 17:14:13.826 [pool-1-thread-2] task2 실행
        2024-10-12 17:14:13.827 [pool-1-thread-3] task3 실행
        2024-10-12 17:14:14.832 [pool-1-thread-1] task1 완료
        2024-10-12 17:14:14.833 [     main] value = 1000
        2024-10-12 17:14:14.833 [pool-1-thread-2] 인터럽트 발생, sleep interrupted
        2024-10-12 17:14:14.833 [pool-1-thread-3] 인터럽트 발생, sleep interrupted
     */
}
