package thread.start;

import static util.MyLogger.log;

public class ManyThreadMainV1 {

    public static void main(String[] args) {
        log("main() start");

        HelloRunnable runnable = new HelloRunnable();

        Thread thread1 = new Thread(runnable);
        thread1.start();
        Thread thread2 = new Thread(runnable);
        thread2.start();
        Thread thread3 = new Thread(runnable);
        thread3.start();

        log("main() end");
    }
    /*
        실행 결과
        2024-09-25 21:54:44.031 [     main] main() start
        2024-09-25 21:54:44.033 [     main] main() end
        Thread-1: run()
        Thread-2: run()
        Thread-0: run()
     */
}
