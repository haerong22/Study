package thread.cas.increment;

import java.util.ArrayList;
import java.util.List;

import static util.ThreadUtils.sleep;

public class IncrementThreadMain {

    public static final int THREAD_COUNT = 1000;

    public static void main(String[] args) throws InterruptedException {
        test(new BasicInteger());
        test(new VolatileInteger());
        test(new SyncInteger());
        test(new MyAtomicInteger());
    }

    /*
        BasicInteger result: 983
        VolatileInteger result: 974
        SyncInteger result: 1000
        MyAtomicInteger result: 1000
     */

    private static void test(IncrementInteger incrementInteger) throws InterruptedException {

        Runnable runnable = () -> {
            sleep(10);
            incrementInteger.increment();
        };

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int result = incrementInteger.get();
        System.out.println(incrementInteger.getClass().getSimpleName() + " result: " + result);
    }
}
