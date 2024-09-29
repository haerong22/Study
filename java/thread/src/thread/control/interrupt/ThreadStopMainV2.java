package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * thread.interrupt() 사용
 * - InterruptedException 을 발생 시키는 코드를 만나면 반응
 * - 작업 중단 지시 후 바로 이루어짐
 */
public class ThreadStopMainV2 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(4000);
        log("작업 중단 지시 thread.interrupt()");
        thread.interrupt(); // interrupt 상태 변경(false -> true)
        log("work 쓰레드 인터럽트 상태1 = " + thread.isInterrupted());
    }
    /*
        2024-09-29 20:01:44.712 [     work] 작업 중
        2024-09-29 20:01:47.717 [     work] 작업 중
        2024-09-29 20:01:48.695 [     main] 작업 중단 지시 thread.interrupt()
        2024-09-29 20:01:48.702 [     main] work 쓰레드 인터럽트 상태1 = true
        2024-09-29 20:01:48.703 [     work] work 쓰레드 인터럽트 상태2 = false
        2024-09-29 20:01:48.704 [     work] interrupt message = sleep interrupted
        2024-09-29 20:01:48.704 [     work] state = RUNNABLE
        2024-09-29 20:01:48.704 [     work] 자원 정리
        2024-09-29 20:01:48.704 [     work] 자원 종료
     */

    static class MyTask implements Runnable {

        @Override
        public void run() {

            try {
                while (true) { // 인터럽트 체크 X
                    log("작업 중");
                    Thread.sleep(3000); // 인터럽트 예외 발생
                }
            } catch (InterruptedException e) { // 예외 발생시 인터럽트 상태 변경(true -> false)
                log("work 쓰레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted());
                log("interrupt message = " + e.getMessage());
                log("state = " + Thread.currentThread().getState());
            }
            log("자원 정리");
            log("자원 종료");
        }
    }
}
