package thread.collection.simple.list;

import static util.MyLogger.log;

public class SimpleListMainV2 {

    public static void main(String[] args) throws InterruptedException {
//        test(new BasicList());
//        test(new SyncList());
        test(new SyncProxyList(new BasicList()));
    }

    /*
        BasicList

        2024-10-09 11:48:53.459 [     main] BasicList
        2024-10-09 11:48:53.565 [ Thread-1] Thread-1: list.add(A)
        2024-10-09 11:48:53.565 [ Thread-2] Thread-2: list.add(B)
        2024-10-09 11:48:53.567 [     main] [B, null] size=2, capacity=5

        SyncList

        2024-10-09 11:56:25.155 [     main] SyncList
        2024-10-09 11:56:25.265 [ Thread-1] Thread-1: list.add(A)
        2024-10-09 11:56:25.371 [ Thread-2] Thread-2: list.add(B)
        2024-10-09 11:56:25.395 [     main] [A, B] size=2, capacity=5

        SyncProxyList

        2024-10-09 12:03:32.133 [     main] SyncProxyList
        2024-10-09 12:03:32.234 [ Thread-1] Thread-1: list.add(A)
        2024-10-09 12:03:32.336 [ Thread-2] Thread-2: list.add(B)
        2024-10-09 12:03:32.337 [     main] [A, B] size=2, capacity=5 by SyncProxyList
     */

    private static void test(SimpleList list) throws InterruptedException {
        log(list.getClass().getSimpleName());

        Runnable addA = () -> {
            list.add("A");
            log("Thread-1: list.add(A)");
        };

        Runnable addB = () -> {
            list.add("B");
            log("Thread-2: list.add(B)");
        };

        Thread thread1 = new Thread(addA, "Thread-1");
        Thread thread2 = new Thread(addB, "Thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        log(list);
    }
}
