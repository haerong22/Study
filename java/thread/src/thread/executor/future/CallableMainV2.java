package thread.executor.future;

import java.util.Random;
import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CallableMainV2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log("submit() 호출");
        Future<Integer> future = es.submit(new MyCallable());
        log("future 즉시 반환, future = " + future);

        log("future.get() [블로킹 메소드 호출 시작 -> main 쓰레드 WAITING");
        Integer result = future.get();
        log("future.get() [블로킹 메소드 호출 완료 -> main 쓰레드 RUNNABLE");

        log("result value = " + result);
        log("future 완료, future = " + future);
        es.close();
    }

    /*
        2024-10-11 21:00:20.935 [     main] submit() 호출
        2024-10-11 21:00:20.937 [pool-1-thread-1] Callable 시작
        2024-10-11 21:00:20.938 [     main] future 즉시 반환, future = java.util.concurrent.FutureTask@1f17ae12[Not completed, task = thread.executor.future.CallableMainV2$MyCallable@300ffa5d]
        2024-10-11 21:00:20.938 [     main] future.get() [블로킹 메소드 호출 시작 -> main 쓰레드 WAITING
        2024-10-11 21:00:22.947 [pool-1-thread-1] create value = 3
        2024-10-11 21:00:22.947 [pool-1-thread-1] Callable 완료
        2024-10-11 21:00:22.947 [     main] future.get() [블로킹 메소드 호출 완료 -> main 쓰레드 RUNNABLE
        2024-10-11 21:00:22.947 [     main] result value = 3
        2024-10-11 21:00:22.948 [     main] future 완료, future = java.util.concurrent.FutureTask@1f17ae12[Completed normally]
     */

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() {
            log("Callable 시작");
            sleep(2000);
            int value = new Random().nextInt(10);
            log("create value = " + value);
            log("Callable 완료");
            return value;
        }
    }
}
