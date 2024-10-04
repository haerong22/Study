package thread.sync.lock;

import java.util.concurrent.locks.LockSupport;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * LockSupport
 * - parkNanos(nanos) : 지정한 나노초동안 TIMED_WAITING 상태로 변경
 */
public class LockSupportMainV2 {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new ParkTest(), "Thread-1");
        thread1.start();

        sleep(100);
        log("Thread-1 state: " + thread1.getState());
    }
    /*
        2024-10-04 20:45:34.593 [ Thread-1] park 시작
        2024-10-04 20:45:34.682 [     main] Thread-1 state: TIMED_WAITING
        2024-10-04 20:45:36.598 [ Thread-1] park 종료, state: RUNNABLE
        2024-10-04 20:45:36.602 [ Thread-1] 인터럽트 상태: false
     */

    static class ParkTest implements Runnable {

        @Override
        public void run() {
            log("park 시작");
            LockSupport.parkNanos(2000_000000); // 2초
            log("park 종료, state: " + Thread.currentThread().getState());
            log("인터럽트 상태: " + Thread.currentThread().isInterrupted());
        }
    }
}
