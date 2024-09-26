package thread.start.test;

import util.MyLogger;

/**
 * - 1~5 까지 1초 간격 출력
 * - Thread 클래스 상속
 *
 * 2024-09-25 22:13:59.273 [ Thread-0] value: 1
 * 2024-09-25 22:14:00.279 [ Thread-0] value: 2
 * 2024-09-25 22:14:01.280 [ Thread-0] value: 3
 * 2024-09-25 22:14:02.288 [ Thread-0] value: 4
 * 2024-09-25 22:14:03.298 [ Thread-0] value: 5
 */
public class StartTest1Main {

    public static void main(String[] args) {
        CounterThread thread = new CounterThread();
        thread.start();
    }

    static class CounterThread extends Thread {

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                MyLogger.log("value: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
