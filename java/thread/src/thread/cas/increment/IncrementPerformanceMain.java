package thread.cas.increment;

public class IncrementPerformanceMain {

    private static final long COUNT = 100_000_000;

    public static void main(String[] args) {
        test(new BasicInteger());
        test(new VolatileInteger());
        test(new SyncInteger());
        test(new MyAtomicInteger());
    }

    /*
        BasicInteger: 58ms          CPU 캐시 사용, 멀티쓰레드 X
        VolatileInteger: 656ms      메인 메모리 사용, 멀티쓰레드 X
        SyncInteger: 936ms          멀티쓰레드 O
        MyAtomicInteger: 723ms      멀티쓰레드 O, 락을 사용하지 않고 원자적 연산(CAS)
     */

    private static void test(IncrementInteger incrementInteger) {
        long startMs = System.currentTimeMillis();

        for (long i = 0; i < COUNT; i++) {
            incrementInteger.increment();
        }

        long endMs = System.currentTimeMillis();
        System.out.println(incrementInteger.getClass().getSimpleName() + ": " + (endMs - startMs) + "ms");
    }
}
