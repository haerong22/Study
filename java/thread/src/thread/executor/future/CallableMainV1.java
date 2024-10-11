package thread.executor.future;

import java.util.Random;
import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CallableMainV1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Integer> future = es.submit(new MyCallable());

        Integer result = future.get();
        log("result value = " + result);
        es.close();
    }

    /*
        2024-10-11 20:49:26.833 [pool-1-thread-1] Callable 시작
        2024-10-11 20:49:28.843 [pool-1-thread-1] create value = 5
        2024-10-11 20:49:28.843 [pool-1-thread-1] Callable 완료
        2024-10-11 20:49:28.843 [     main] result value = 5
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
