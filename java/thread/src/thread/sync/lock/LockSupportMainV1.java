package thread.sync.lock;

import java.util.concurrent.locks.LockSupport;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * LockSupport
 * - park() : 쓰레드 상태 변경 RUNNABLE -> WAITING
 * - unpark(thread) : 쓰레드 상태 변경 WAITING -> RUNNABLE
 * - WAITING 상태 이므로 interrupt() 동작
 */
public class LockSupportMainV1 {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new ParkTest(), "Thread-1");
        thread1.start();

        sleep(100);
        log("Thread-1 state: " + thread1.getState());

        log("main -> unpark(Thread-1)");
        // LockSupport.unpark(thread1); // 1. unpark() 사용
        thread1.interrupt(); // 2. interrupt() 사용
    }
    /*
        1. unpark()
        2024-10-04 20:40:13.748 [ Thread-1] park 시작
        2024-10-04 20:40:13.839 [     main] Thread-1 state: WAITING
        2024-10-04 20:40:13.839 [     main] main -> unpark(Thread-1)
        2024-10-04 20:40:13.840 [ Thread-1] park 종료, state: RUNNABLE
        2024-10-04 20:40:13.842 [ Thread-1] 인터럽트 상태: false

        2. interrupt()
        2024-10-04 20:43:45.071 [ Thread-1] park 시작
        2024-10-04 20:43:45.155 [     main] Thread-1 state: WAITING
        2024-10-04 20:43:45.155 [     main] main -> unpark(Thread-1)
        2024-10-04 20:43:45.156 [ Thread-1] park 종료, state: RUNNABLE
        2024-10-04 20:43:45.159 [ Thread-1] 인터럽트 상태: true
     */

    static class ParkTest implements Runnable {

        @Override
        public void run() {
            log("park 시작");
            LockSupport.park();
            log("park 종료, state: " + Thread.currentThread().getState());
            log("인터럽트 상태: " + Thread.currentThread().isInterrupted());
        }
    }
}
