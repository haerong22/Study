package thread.control.join;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV2 {

    public static void main(String[] args) {
        log("start");
        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);

        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");

        thread1.start();
        thread2.start();

        // sleep 활용 - 정확한 타이밍을 맞추어 기다리기 어려움
        log("main 쓰레드 sleep()");
        sleep(3000);
        log("main 쓰레드 깨어남");

        log("task1.result = " + task1.result);
        log("task2.result = " + task2.result);

        int sumAll = task1.result + task2.result;
        log("task1 + task2 = " + sumAll);

        log("end");
    }
    /*
        2024-09-28 19:44:01.333 [     main] start
        2024-09-28 19:44:01.334 [     main] main 쓰레드 sleep()
        2024-09-28 19:44:01.334 [ thread-2] 작업 시작
        2024-09-28 19:44:01.334 [ thread-1] 작업 시작
        2024-09-28 19:44:03.344 [ thread-2] 작업 완료 result = 3775
        2024-09-28 19:44:03.344 [ thread-1] 작업 완료 result = 1275
        2024-09-28 19:44:04.340 [     main] main 쓰레드 깨어남
        2024-09-28 19:44:04.342 [     main] task1.result = 1275
        2024-09-28 19:44:04.342 [     main] task2.result = 3775
        2024-09-28 19:44:04.343 [     main] task1 + task2 = 5050
        2024-09-28 19:44:04.343 [     main] end
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
