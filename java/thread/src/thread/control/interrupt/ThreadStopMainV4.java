package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * thread.interrupt() 사용
 * - InterruptedException 을 발생 시키는 코드를 만나면 반응
 * - 작업 중단 지시 후 바로 이루어짐
 * - 인터럽트 상태 확인 Thread.interrupted() 사용 -> 인터럽트 상태 확인 후 상태 변경(false)
 */
public class ThreadStopMainV4 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(100);
        log("작업 중단 지시 thread.interrupt()");
        thread.interrupt();
        log("work 쓰레드 인터럽트 상태1 = " + thread.isInterrupted());
    }
    /*
        ...

        2024-09-29 20:28:27.582 [     work] 작업 중
        2024-09-29 20:28:27.583 [     work] 작업 중
        2024-09-29 20:28:27.582 [     main] 작업 중단 지시 thread.interrupt()
        2024-09-29 20:28:27.583 [     work] 작업 중
        2024-09-29 20:28:27.587 [     main] work 쓰레드 인터럽트 상태1 = true
        2024-09-29 20:28:27.588 [     work] work 쓰레드 인터럽트 상태2 = false
        2024-09-29 20:28:27.588 [     work] 자원 정리 시도
        2024-09-29 20:28:28.593 [     work] 자원 정리 종료
        2024-09-29 20:28:28.593 [     work] 작업 종료
     */

    static class MyTask implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) { // 인터럽트 상태 변경 O
                log("작업 중");
            }
            log("work 쓰레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted()); // 인터럽트 상태 false

            try {
                log("자원 정리 시도");
                Thread.sleep(1000); // 인터럽트 상태가 false 이므로 예외 발생 X
                log("자원 정리 종료");
            } catch (InterruptedException e) {
                log("자원 정리 실패 - 자원 정리 중 인터럽트 발생");
                log("work 쓰레드 인터럽스 상태3 = " + Thread.currentThread().isInterrupted());
            }
            log("작업 종료");
        }
    }
}
