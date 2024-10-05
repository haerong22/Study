package thread.bounded;

import java.util.ArrayList;
import java.util.List;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BoundedMain {

    public static void main(String[] args) {
        // 1. BoundedQueue 선택
        BoundedQueue queue = new BoundedQueueV1(2);

        // 2. 생산자, 소비자 실행 순서 선택
        producerFirst(queue);
//        consumerFirst(queue);
    }

    /*
        BoundedQueueV1 - producerFirst

        2024-10-05 16:56:09.963 [     main] == [생산자 먼저 실행] 시작, BoundedQueueV1 ==

        2024-10-05 16:56:09.964 [     main] 생산자 시작
        2024-10-05 16:56:09.971 [producer1] [생산 시도]data1 -> []
        2024-10-05 16:56:09.971 [producer1] [생산 완료]data1 -> [data1]
        2024-10-05 16:56:10.072 [producer2] [생산 시도]data2 -> [data1]
        2024-10-05 16:56:10.073 [producer2] [생산 완료]data2 -> [data1, data2]
        2024-10-05 16:56:10.177 [producer3] [생산 시도]data3 -> [data1, data2]
        2024-10-05 16:56:10.177 [producer3] [put] 큐가 가득 참, 버림: data3
        2024-10-05 16:56:10.177 [producer3] [생산 완료]data3 -> [data1, data2]

        2024-10-05 16:56:10.282 [     main] 현재 상태 출력, 큐 데이터: [data1, data2]
        2024-10-05 16:56:10.282 [     main] producer1: TERMINATED
        2024-10-05 16:56:10.282 [     main] producer2: TERMINATED
        2024-10-05 16:56:10.282 [     main] producer3: TERMINATED

        2024-10-05 16:56:10.283 [     main] 소비자 시작
        2024-10-05 16:56:10.283 [consumer1] [소비 시도]     ? <- [data1, data2]
        2024-10-05 16:56:10.284 [consumer1] [소비 완료] data1 <- [data2]
        2024-10-05 16:56:10.386 [consumer2] [소비 시도]     ? <- [data2]
        2024-10-05 16:56:10.386 [consumer2] [소비 완료] data2 <- []
        2024-10-05 16:56:10.490 [consumer3] [소비 시도]     ? <- []
        2024-10-05 16:56:10.490 [consumer3] [소비 완료] null <- []

        2024-10-05 16:56:10.595 [     main] 현재 상태 출력, 큐 데이터: []
        2024-10-05 16:56:10.595 [     main] producer1: TERMINATED
        2024-10-05 16:56:10.595 [     main] producer2: TERMINATED
        2024-10-05 16:56:10.595 [     main] producer3: TERMINATED
        2024-10-05 16:56:10.595 [     main] consumer1: TERMINATED
        2024-10-05 16:56:10.595 [     main] consumer2: TERMINATED
        2024-10-05 16:56:10.596 [     main] consumer3: TERMINATED
        2024-10-05 16:56:10.596 [     main] == [생산자 먼저 실행] 종료, BoundedQueueV1 ==

        BoundedQueueV1 - consumerFirst

        2024-10-05 16:56:49.499 [     main] == [소비자 먼저 실행] 시작, BoundedQueueV1 ==

        2024-10-05 16:56:49.500 [     main] 소비자 시작
        2024-10-05 16:56:49.503 [consumer1] [소비 시도]     ? <- []
        2024-10-05 16:56:49.506 [consumer1] [소비 완료] null <- []
        2024-10-05 16:56:49.605 [consumer2] [소비 시도]     ? <- []
        2024-10-05 16:56:49.605 [consumer2] [소비 완료] null <- []
        2024-10-05 16:56:49.710 [consumer3] [소비 시도]     ? <- []
        2024-10-05 16:56:49.710 [consumer3] [소비 완료] null <- []

        2024-10-05 16:56:49.814 [     main] 현재 상태 출력, 큐 데이터: []
        2024-10-05 16:56:49.816 [     main] consumer1: TERMINATED
        2024-10-05 16:56:49.816 [     main] consumer2: TERMINATED
        2024-10-05 16:56:49.816 [     main] consumer3: TERMINATED

        2024-10-05 16:56:49.816 [     main] 생산자 시작
        2024-10-05 16:56:49.818 [producer1] [생산 시도]data1 -> []
        2024-10-05 16:56:49.818 [producer1] [생산 완료]data1 -> [data1]
        2024-10-05 16:56:49.922 [producer2] [생산 시도]data2 -> [data1]
        2024-10-05 16:56:49.923 [producer2] [생산 완료]data2 -> [data1, data2]
        2024-10-05 16:56:50.023 [producer3] [생산 시도]data3 -> [data1, data2]
        2024-10-05 16:56:50.023 [producer3] [put] 큐가 가득 참, 버림: data3
        2024-10-05 16:56:50.024 [producer3] [생산 완료]data3 -> [data1, data2]

        2024-10-05 16:56:50.128 [     main] 현재 상태 출력, 큐 데이터: [data1, data2]
        2024-10-05 16:56:50.128 [     main] consumer1: TERMINATED
        2024-10-05 16:56:50.129 [     main] consumer2: TERMINATED
        2024-10-05 16:56:50.129 [     main] consumer3: TERMINATED
        2024-10-05 16:56:50.129 [     main] producer1: TERMINATED
        2024-10-05 16:56:50.129 [     main] producer2: TERMINATED
        2024-10-05 16:56:50.129 [     main] producer3: TERMINATED
        2024-10-05 16:56:50.130 [     main] == [소비자 먼저 실행] 종료, BoundedQueueV1 ==
     */

    private static void consumerFirst(BoundedQueue queue) {
        log("== [소비자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startConsumer(queue, threads);
        printAllState(queue, threads);
        startProducer(queue, threads);
        printAllState(queue, threads);
        log("== [소비자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void producerFirst(BoundedQueue queue) {
        log("== [생산자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startProducer(queue, threads);
        printAllState(queue, threads);
        startConsumer(queue, threads);
        printAllState(queue, threads);
        log("== [생산자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void startProducer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("생산자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread producer = new Thread(new ProducerTask(queue, "data" + i), "producer" + i);
            threads.add(producer);
            producer.start();
            sleep(100);
        }
    }

    private static void startConsumer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("소비자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread consumer = new Thread(new ConsumerTask(queue), "consumer" + i);
            threads.add(consumer);
            consumer.start();
            sleep(100);
        }
    }

    private static void printAllState(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("현재 상태 출력, 큐 데이터: " + queue);
        for (Thread thread : threads) {
            log(thread.getName() + ": " + thread.getState());
        }
    }

}
