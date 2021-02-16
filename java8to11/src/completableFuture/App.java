package completableFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    private static Runnable getRunnable(String message) {
        return () -> System.out.println(message + ": " + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {

        /**
         * Executors 
         */

        // newSingleThreadScheduledExecutor
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(getRunnable("Hello"), 3, TimeUnit.SECONDS); // 3초 후 실행
        executorService.scheduleAtFixedRate(getRunnable("World"), 1, 2, TimeUnit.SECONDS); // 1초 후 2초마다 실행


//        // newFixedThreadPool
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        executorService.submit(getRunnable("Hello"));
//        executorService.submit(getRunnable("World"));
//        executorService.submit(getRunnable("The"));
//        executorService.submit(getRunnable("Java"));
//        executorService.submit(getRunnable("Thread"));
//
//        executorService.shutdown();


//        // newSingleThreadExecutor
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(() -> {
//            System.out.println("Thread: " + Thread.currentThread().getName());
//        });
//
//        executorService.shutdown(); // shutdown() : graceful,  shutdownNow() : 즉시 종료




        /**
         * Thread
         */

//        // join
//        Thread thread = new Thread(() -> {
//            System.out.println("Thread: " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(3000L);
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//            }
//        });
//        thread.start();
//
//        System.out.println("Hello: " + Thread.currentThread().getName());
//        thread.join();
//        System.out.println(thread + " is finished");


//        // interrupt
//        Thread thread = new Thread(() -> {
//            while (true) {
//                System.out.println("Thread: " + Thread.currentThread().getName());
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    System.out.println("exit!");
//                    return;
//                }
//            }
//        });
//        thread.start();
//        System.out.println("Hello: " + Thread.currentThread().getName());
//        Thread.sleep(3000L);
//        thread.interrupt();


//        // sleep
//        Thread thread = new Thread(() -> {
//            try {
//                Thread.sleep(1000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Thread: " + Thread.currentThread().getName());
//        });
//        thread.start();
//        System.out.println("Hello: " + Thread.currentThread().getName());


//        // 람다 사용
//        Thread thread = new Thread(() -> {
//            System.out.println("Thread: " + Thread.currentThread().getName());
//        });
//        thread.start();
//        System.out.println("Hello: " + Thread.currentThread().getName());


//        // 익명 클래스 사용
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Thread: " + Thread.currentThread().getName());
//            }
//        });
//        thread.start();
//        System.out.println("Hello: " + Thread.currentThread().getName());


//        // Thread 상속해서 사용
//        MyThread myThread = new MyThread();
//        myThread.start();
//
//        System.out.println("Hello: " + Thread.currentThread().getName());
    }
}
