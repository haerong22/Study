package thread.start.test;

import static util.MyLogger.log;

/**
 * - 1~5 까지 1초 간격 출력
 * - Runnable 익명 클래스로 구현
 * - 쓰레드 이름 counter
 *
 * 2024-09-25 22:21:36.162 [  counter] value: 1
 * 2024-09-25 22:21:37.168 [  counter] value: 2
 * 2024-09-25 22:21:38.171 [  counter] value: 3
 * 2024-09-25 22:21:39.177 [  counter] value: 4
 * 2024-09-25 22:21:40.180 [  counter] value: 5
 */
public class StartTest3Main {

    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    log("value: " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Thread thread = new Thread(runnable, "counter");

        thread.start();
    }
}
