package thread.start.test;

import static util.MyLogger.log;

/**
 * - Thread-A : 1초에 한번씩 "A" 출력
 * - Thread-B : 0.5초에 한번씩 "B" 출력
 * - 강제종료 전까지 계속 실행
 * <p>
 * 2024-09-25 22:31:09.374 [ Thread-A] A
 * 2024-09-25 22:31:09.374 [ Thread-B] B
 * 2024-09-25 22:31:09.902 [ Thread-B] B
 * 2024-09-25 22:31:10.376 [ Thread-A] A
 * 2024-09-25 22:31:10.413 [ Thread-B] B
 *
 * ...
 *
 *
 */
public class StartTest4Main {

    public static void main(String[] args) {

        Thread threadA = new Thread(new PrintRunnable("A", 1000), "Thread-A");
        Thread threadB = new Thread(new PrintRunnable("B", 500), "Thread-B");

        threadA.start();
        threadB.start();
    }

    static class PrintRunnable implements Runnable {

        private final String text;
        private final int sleepMillis;

        public PrintRunnable(String text, int sleepMillis) {
            this.text = text;
            this.sleepMillis = sleepMillis;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    log(text);
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
