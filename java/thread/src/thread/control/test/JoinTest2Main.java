package thread.control.test;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinTest2Main {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new MyTask(), "t1");
        Thread t2 = new Thread(new MyTask(), "t2");
        Thread t3 = new Thread(new MyTask(), "t3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        log("모든 쓰레드 실행 완료"); // 3초
    }

    /*
        2024-09-28 20:00:37.198 [       t2] 1
        2024-09-28 20:00:37.198 [       t1] 1
        2024-09-28 20:00:37.198 [       t3] 1
        2024-09-28 20:00:38.201 [       t3] 2
        2024-09-28 20:00:38.201 [       t1] 2
        2024-09-28 20:00:38.204 [       t2] 2
        2024-09-28 20:00:39.207 [       t3] 3
        2024-09-28 20:00:39.208 [       t1] 3
        2024-09-28 20:00:39.210 [       t2] 3
        2024-09-28 20:00:40.213 [     main] 모든 쓰레드 실행 완료
     */

    static class MyTask implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <=3 ; i++) {
                log(i);
                sleep(1000);
            }
        }
    }
}
