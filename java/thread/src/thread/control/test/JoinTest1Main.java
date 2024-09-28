package thread.control.test;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinTest1Main {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new MyTask(), "t1");
        Thread t2 = new Thread(new MyTask(), "t2");
        Thread t3 = new Thread(new MyTask(), "t3");

        t1.start(); // 3초
        t1.join(); // 대기

        t2.start(); // 3초
        t2.join(); // 대기

        t3.start(); // 3초
        t3.join(); // 대기

        log("모든 쓰레드 실행 완료"); // 9초
    }

    /*
        2024-09-28 19:59:18.696 [       t1] 1
        2024-09-28 19:59:19.699 [       t1] 2
        2024-09-28 19:59:20.702 [       t1] 3
        2024-09-28 19:59:21.706 [       t2] 1
        2024-09-28 19:59:22.715 [       t2] 2
        2024-09-28 19:59:23.721 [       t2] 3
        2024-09-28 19:59:24.727 [       t3] 1
        2024-09-28 19:59:25.732 [       t3] 2
        2024-09-28 19:59:26.738 [       t3] 3
        2024-09-28 19:59:27.744 [     main] 모든 쓰레드 실행 완료
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
