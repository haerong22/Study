package thread.volatile_;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * 메모리 가시성(memory visibility)
 * 멀티 쓰레드 환경에서 한 쓰레드가 변경한 값이 다른 쓰레드에 언제 보이는지
 */
public class VolatileFlagMainV0 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread t = new Thread(task, "work");
        log("runFlag = " + task.runFlag);
        t.start();

        sleep(1000);
        log("runFlag 를 false 로 변경 시도");
        task.runFlag = false;
        log("runFlag = " + task.runFlag);
        log("main 종료");
    }

    /*
        2024-10-02 23:41:57.770 [     main] runFlag = true
        2024-10-02 23:41:57.770 [     work] task 시작
        2024-10-02 23:41:58.772 [     main] runFlag 를 false 로 변경 시도
        2024-10-02 23:41:58.772 [     main] runFlag = false
        2024-10-02 23:41:58.773 [     main] main 종료

        ... work 쓰레드 종료 안됨(work 쓰레드를 작업하고 있는 CPU 코어의 캐시 메모리 값을 읽기 때문)
     */

    static class MyTask implements Runnable {

        boolean runFlag = true;

        @Override
        public void run() {
            log("task 시작");

            while (runFlag) {
                // runFlag 가 false 면 탈출
            }

            log("task 종료");
        }
    }
}
