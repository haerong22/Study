package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * runFlag 변수 사용
 * - 작업 중단 지시 후 바로 이루어지지 않음
 */
public class ThreadStopMainV1 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(4000);
        log("작업 중단 지시 runFlag = false");
        task.runFlag = false;
    }
    /*
        2024-09-29 19:15:57.572 [     work] 작업 중
        2024-09-29 19:16:00.578 [     work] 작업 중
        2024-09-29 19:16:01.561 [     main] 작업 중단 지시 runFlag = false
        2024-09-29 19:16:03.583 [     work] 자원 정리
        2024-09-29 19:16:03.584 [     work] 자원 종료
     */

    static class MyTask implements Runnable {

        volatile boolean runFlag = true;

        @Override
        public void run() {
            while (runFlag) {
                log("작업 중");
                sleep(3000);
            }

            log("자원 정리");
            log("자원 종료");
        }
    }
}
