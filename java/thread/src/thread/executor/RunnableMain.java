package thread.executor;

import java.util.Random;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class RunnableMain {

    public static void main(String[] args) throws InterruptedException {
        MyRunnable task = new MyRunnable();
        Thread thread = new Thread(task, "Thread-1");
        thread.start();
        thread.join();
        int result = task.value;
        log("result value = " + result);
    }

    /*
        2024-10-10 23:44:11.350 [ Thread-1] Runnable 시작
        2024-10-10 23:44:13.360 [ Thread-1] create value = 9
        2024-10-10 23:44:13.360 [ Thread-1] Runnable 완료
        2024-10-10 23:44:13.360 [     main] result value = 9
     */

    static class MyRunnable implements Runnable {
        int value;

        @Override
        public void run() {
            log("Runnable 시작");
            sleep(2000);
            value = new Random().nextInt(10);
            log("create value = " + value);
            log("Runnable 완료");
        }
    }
}
