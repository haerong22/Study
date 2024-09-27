package thread.control;

import static util.MyLogger.log;

public class ThreadStateMain {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new MyRunnable(), "myThread");
        log("myThread.state1 = " + thread.getState()); // NEW
        log("myThread.start()");
        thread.start();
        Thread.sleep(1000);
        log("myThread.state3 = " + thread.getState()); // TIMED_WAITING
        Thread.sleep(4000);
        log("myThread.state5 = " + thread.getState()); // TERMINATED
        log("end");
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            try {
                log("start");
                log("myThread.state2 = " + Thread.currentThread().getState()); // RUNNABLE
                log("sleep() start");
                Thread.sleep(3000);
                log("sleep() end");
                log("myThread.state4 = " + Thread.currentThread().getState()); // RUNNABLE
                log("end");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
        2024-09-27 01:44:07.608 [     main] myThread.state1 = NEW
        2024-09-27 01:44:07.610 [     main] myThread.start()
        2024-09-27 01:44:07.613 [ myThread] start
        2024-09-27 01:44:07.613 [ myThread] myThread.state2 = RUNNABLE
        2024-09-27 01:44:07.613 [ myThread] sleep() start
        2024-09-27 01:44:08.617 [     main] myThread.state3 = TIMED_WAITING
        2024-09-27 01:44:10.613 [ myThread] sleep() end
        2024-09-27 01:44:10.614 [ myThread] myThread.state4 = RUNNABLE
        2024-09-27 01:44:10.614 [ myThread] end
        2024-09-27 01:44:12.620 [     main] myThread.state5 = TERMINATED
        2024-09-27 01:44:12.620 [     main] end
     */
}
