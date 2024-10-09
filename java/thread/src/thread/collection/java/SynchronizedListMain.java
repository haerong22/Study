package thread.collection.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static util.MyLogger.log;

public class SynchronizedListMain {

    public static void main(String[] args) throws InterruptedException {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        test(list);
    }

    /*
        2024-10-09 12:23:42.929 [     main] SynchronizedRandomAccessList
        2024-10-09 12:23:42.931 [ Thread-1] Thread-1: list.add(A)
        2024-10-09 12:23:42.931 [ Thread-2] Thread-2: list.add(B)
        2024-10-09 12:23:42.932 [     main] [A, B]
     */

    private static void test(List<String> list) throws InterruptedException {
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
