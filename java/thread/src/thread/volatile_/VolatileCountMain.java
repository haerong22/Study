package thread.volatile_;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class VolatileCountMain {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread t = new Thread(task, "work");
        t.start();

        sleep(1000);

        task.flag = false;
        log("flag = " + task.flag + ", count = " + task.count + " in main");
    }

    /*
        volatile 사용 X

        ...

        2024-10-03 00:04:53.546 [     work] flag = true, count = 800000000 in while()
        2024-10-03 00:04:53.649 [     work] flag = true, count = 900000000 in while()
        2024-10-03 00:04:53.726 [     main] flag = false, count = 972829664 in main
        2024-10-03 00:04:53.755 [     work] flag = true, count = 1000000000 in while()
        2024-10-03 00:04:53.755 [     work] flag = false, count = 1000000000 종료

        volatile 사용 O : 메인 메모리에 접근 하여 즉시 종료 하지만 성능 차이 많이 남

        2024-10-03 00:10:46.705 [     work] flag = true, count = 100000000 in while()
        2024-10-03 00:10:46.874 [     work] flag = false, count = 121111028 종료
        2024-10-03 00:10:46.874 [     main] flag = false, count = 121111028 in main
     */

    static class MyTask implements Runnable {

//        boolean flag = true;
//        long count;

        volatile boolean flag = true;
        volatile long count;

        @Override
        public void run() {

            while (flag) {
                count++;

                if (count % 100_000_000 == 0) {
                    log("flag = " + flag + ", count = " + count + " in while()");
                }
            }
            log("flag = " + flag + ", count = " + count + " 종료");
        }
    }
}
