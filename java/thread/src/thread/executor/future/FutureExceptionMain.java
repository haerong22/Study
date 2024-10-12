package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureExceptionMain {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log("작업 전달");
        Future<Integer> future = es.submit(new ExCallable());
        sleep(1000);

        try {
            log("future.get() 호출 시도, future.state(): " + future.state());
            Integer result = future.get();
            log("result value = " + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log("e = " + e);
            Throwable cause = e.getCause(); // 원본 예외
            log("cause = " + cause);
        }

        es.close();
    }

    /*
        2024-10-12 17:04:22.919 [     main] 작업 전달
        2024-10-12 17:04:22.921 [pool-1-thread-1] Callable 실행, 예외 발생
        2024-10-12 17:04:23.924 [     main] future.get() 호출 시도, future.state(): FAILED
        2024-10-12 17:04:23.925 [     main] e = java.util.concurrent.ExecutionException: java.lang.IllegalStateException: ex!
        2024-10-12 17:04:23.925 [     main] cause = java.lang.IllegalStateException: ex!
     */

    static class ExCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            log("Callable 실행, 예외 발생");
            throw new IllegalStateException("ex!");
        }
    }
}
