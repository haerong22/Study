package virtualthread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        Thread t = Thread.ofVirtual()
                .start(() -> printlnWithThread("Hello World"));

        Thread t2 = Thread.startVirtualThread(() -> printlnWithThread("Hello World!"));

        t.join();
        t2.join();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<?> future = executor.submit(() -> printlnWithThread("Hello World!!"));
            future.get();
        }
    }

    private static void printlnWithThread(Object o) {
        System.out.printf("[%s] %s\n", Thread.currentThread().getName(), o);
    }
}
