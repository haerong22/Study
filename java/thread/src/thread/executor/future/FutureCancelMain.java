package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureCancelMain {

    private static boolean mayInterruptIfRunning = true; // 취소상태로 변경, 실행중인 작업도 중지
//    private static boolean mayInterruptIfRunning = false;  // 취소상태로 변경, 실행중인 작업은 취소 X

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> future = es.submit(new MyTask());
        log("Future.state: " + future.state());

        sleep(3000);

        // cancel() 호출
        log("future.cancel(" + mayInterruptIfRunning + ") 호출");
        boolean cancelResult = future.cancel(mayInterruptIfRunning);
        log("cancel(" + mayInterruptIfRunning + ") result: " + cancelResult);

        try {
            log("Future result: " + future.get());
        } catch (CancellationException e) {
            log("Future는 이미 취소되었습니다.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        es.close();
    }

    /*
        2024-10-12 16:57:05.610 [pool-1-thread-1] 작업 중: 0
        2024-10-12 16:57:05.610 [     main] Future.state: RUNNING
        2024-10-12 16:57:06.614 [pool-1-thread-1] 작업 중: 1
        2024-10-12 16:57:07.619 [pool-1-thread-1] 작업 중: 2
        2024-10-12 16:57:08.616 [     main] future.cancel(true) 호출
        2024-10-12 16:57:08.617 [pool-1-thread-1] 인터럽트 발생
        2024-10-12 16:57:08.622 [     main] cancel(true) result: true
        2024-10-12 16:57:08.622 [     main] Future는 이미 취소되었습니다.
     */

    static class MyTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            try {
                for (int i = 0; i < 10; i++) {
                    log("작업 중: " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                log("인터럽트 발생");
                return "Interrupted";
            }
            return "Completed";
        }
    }
}
