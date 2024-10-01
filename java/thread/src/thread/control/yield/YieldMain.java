package thread.control.yield;

import static util.ThreadUtils.sleep;

public class YieldMain {

    static final int THREAD_COUNT = 1000;

    public static void main(String[] args) {

        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }
    }

    /*
        1. empty : 특정 쓰레드가 쭉 실행되고 다른 쓰레드가 수행

        ...

        Thread-999 - 0
        Thread-999 - 1
        Thread-999 - 2
        Thread-999 - 3
        Thread-999 - 4
        Thread-999 - 5
        Thread-999 - 6
        Thread-999 - 7
        Thread-999 - 8
        Thread-999 - 9

        ...

        2. sleep(1) : 1밀리초 동안 RUNNABLE -> TIMED_WAITING 상태로 변경 되므로(실행 스케줄링 제외) 다른 쓰레드가 실행

        ...

        Thread-988 - 9
        Thread-997 - 9
        Thread-836 - 9
        Thread-931 - 9
        Thread-954 - 9
        Thread-974 - 9
        Thread-891 - 9
        Thread-936 - 9
        Thread-983 - 9
        Thread-777 - 9

        ...

        3. yield : 다시 스케줄링 큐에 들어가므로 RUNNABLE 상태를 유지하면서 다른 쓰레드에게 CPU 를 양보

        ...

        Thread-886 - 9
        Thread-927 - 9
        Thread-926 - 9
        Thread-931 - 8
        Thread-985 - 8
        Thread-931 - 9
        Thread-881 - 9
        Thread-985 - 9

        ...
     */

    static class MyRunnable implements Runnable {

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
                // 1. empty
                // 2. sleep
                sleep(1);
                // 3. yield
                // Thread.yield();
            }
        }
    }
}
