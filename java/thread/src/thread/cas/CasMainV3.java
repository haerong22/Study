package thread.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CasMainV3 {

    private static final int THREAD_COUNT = 2;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("start value = " + atomicInteger.get());

        Runnable runnable = () -> incrementAndGet(atomicInteger);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int result = atomicInteger.get();
        System.out.println(atomicInteger.getClass().getSimpleName() + " resultValue: " + result);
    }

    /*
        start value = 0
        2024-10-07 23:57:07.001 [ Thread-1] getValue: 0
        2024-10-07 23:57:07.001 [ Thread-0] getValue: 0
        2024-10-07 23:57:07.002 [ Thread-1] result: true
        2024-10-07 23:57:07.002 [ Thread-0] result: false
        2024-10-07 23:57:07.107 [ Thread-0] getValue: 1
        2024-10-07 23:57:07.108 [ Thread-0] result: true
        AtomicInteger resultValue: 2
     */

    private static void incrementAndGet(AtomicInteger atomicInteger) {
        int getValue;
        boolean result;

        do {
            getValue = atomicInteger.get();
            sleep(100);
            log("getValue: " + getValue);
            result = atomicInteger.compareAndSet(getValue, getValue + 1);
            log("result: " + result);
        } while (!result);

    }
}
