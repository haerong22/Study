package thread.volatile_;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * volatile 키워드를 사용하면 캐시 메모리를 사용하지 않고 메인 메모리에 직접 접근
 * 단, 캐시 메모리 사용할 때보다 성능이 낮다
 */
public class VolatileFlagMainV1 {

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
        2024-10-02 23:58:46.229 [     main] runFlag = true
        2024-10-02 23:58:46.230 [     work] task 시작
        2024-10-02 23:58:47.235 [     main] runFlag 를 false 로 변경 시도
        2024-10-02 23:58:47.236 [     work] task 종료
        2024-10-02 23:58:47.236 [     main] runFlag = false
        2024-10-02 23:58:47.236 [     main] main 종료
     */

    static class MyTask implements Runnable {

        volatile boolean runFlag = true;

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
