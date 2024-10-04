package thread.sync.test;

import static util.MyLogger.log;

/**
 * 지역변수 공유는 다른 쓰레드와 공유 X
 */
public class SyncTest2Main {

    public static void main(String[] args) throws InterruptedException {
        MyCounter myCounter = new MyCounter();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                myCounter.count();
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();
    }
    /*
        2024-10-03 17:14:34.847 [ Thread-1] 결과: 1000
        2024-10-03 17:14:34.847 [ Thread-2] 결과: 1000
     */

    static class MyCounter {

        public void count() {
            int localValue = 0;
            for (int i = 0; i < 1000; i++) {
                localValue = localValue + 1;
            }
            log("결과: " + localValue);
        }
    }
}
