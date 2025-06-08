package parallel;

import static util.MyLogger.log;

public class ParallelMain2 {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        SumTask task1 = new SumTask(1, 4);
        SumTask task2 = new SumTask(5, 8);

        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        log("main 쓰레드 대기 완료");

        int sum = task1.result + task2.result;

        long endTime = System.currentTimeMillis();
        log("time: " + (endTime - startTime) + "ms, sum: " + sum);
    }

    /*
        20:33:31.235 [ thread-1] 작업 시작
        20:33:31.235 [ thread-2] 작업 시작
        20:33:31.241 [ thread-1] calculate 1 -> 10
        20:33:31.242 [ thread-2] calculate 5 -> 50
        20:33:32.245 [ thread-2] calculate 6 -> 60
        20:33:32.247 [ thread-1] calculate 2 -> 20
        20:33:33.251 [ thread-2] calculate 7 -> 70
        20:33:33.251 [ thread-1] calculate 3 -> 30
        20:33:34.254 [ thread-1] calculate 4 -> 40
        20:33:34.254 [ thread-2] calculate 8 -> 80
        20:33:35.265 [ thread-1] 작업 완료 result = 100
        20:33:35.265 [ thread-2] 작업 완료 result = 260
        20:33:35.266 [     main] main 쓰레드 대기 완료
        20:33:35.269 [     main] time: 4046ms, sum: 360
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

            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                int calculated = HeavyJob.heavyTask(i);
                sum += calculated;
            }
            result = sum;

            log("작업 완료 result = " + result);
        }
    }
}
