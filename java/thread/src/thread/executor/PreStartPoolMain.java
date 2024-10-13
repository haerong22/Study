package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static thread.executor.ExecutorUtils.printState;

public class PreStartPoolMain {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(100);
        printState(es);
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) es;
        poolExecutor.prestartAllCoreThreads(); // 쓰레드 미리 생성
        printState(es);
    }

    /*
        2024-10-12 22:30:53.811 [     main] [pool=0, active=0, queuedTasks=0, completedTask=0]
        2024-10-12 22:30:53.819 [     main] [pool=100, active=0, queuedTasks=0, completedTask=0]
     */

}
