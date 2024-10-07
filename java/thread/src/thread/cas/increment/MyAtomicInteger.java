package thread.cas.increment;

import java.util.concurrent.atomic.AtomicInteger;

public class MyAtomicInteger implements IncrementInteger {

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void increment() {
        atomicInteger.incrementAndGet();
    }

    @Override
    public int get() {
        return atomicInteger.get();
    }
}
