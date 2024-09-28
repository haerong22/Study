package thread.control.join;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV1 {

    public static void main(String[] args) {
        log("start");
        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);

        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");

        thread1.start();
        thread2.start();

        log("task1.result = " + task1.result);
        log("task2.result = " + task2.result);

        int sumAll = task1.result + task2.result;
        log("task1 + task2 = " + sumAll);

        log("end");
    }
    /*
        2024-09-28 19:30:51.461 [     main] start
        2024-09-28 19:30:51.462 [ thread-1] 작업 시작
        2024-09-28 19:30:51.462 [ thread-2] 작업 시작
        2024-09-28 19:30:51.465 [     main] task1.result = 0
        2024-09-28 19:30:51.465 [     main] task2.result = 0
        2024-09-28 19:30:51.465 [     main] task1 + task2 = 0
        2024-09-28 19:30:51.465 [     main] end
        2024-09-28 19:30:53.468 [ thread-1] 작업 완료 result = 1275
        2024-09-28 19:30:53.469 [ thread-2] 작업 완료 result = 3775
     */

    static class SumTask implements Runnable {

        int startValue;
        int endValue;
        int result = 0;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public void run() {
            log("작업 시작");
            sleep(2000);
            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;
            }
            result = sum;
            log("작업 완료 result = " + result);
        }
    }
}
