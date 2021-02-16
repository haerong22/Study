package completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class App {

    private static Runnable getRunnable(String message) {
        return () -> System.out.println(message + ": " + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        /**
         * Callable, Future
         */

        // invokeAll, invokeAny
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Callable<String> world = () -> {
            Thread.sleep(3000L);
            return "world";
        };

        Callable<String> java = () -> {
            Thread.sleep(1000L);
            return "java";
        };

        // invokeAny
        String future = executorService.invokeAny(Arrays.asList(hello, world, java)); // 가장 먼저 도착한 값
        System.out.println(future);

        // invokeAll
        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, world, java)); // 다 끝날때 까지 대기
        for (Future<String> f : futures) {
            System.out.println(f.get());
        }

        executorService.shutdown();


//      // Callable - get, cancel
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        Callable<String> hello = () -> {
//            Thread.sleep(2000L);
//            return "Hello";
//        };
//
//        Future<String> helloFuture = executorService.submit(hello);
//
//        System.out.println(helloFuture.isDone());
//        System.out.println("Start!");
//
////        System.out.println(helloFuture.get()); // 결과 까지 대기 ( blocking call )
//        helloFuture.cancel(false); // true : 현재 진행중인 작업을 interrupt 후 종료,
//                                                     // false : 대기 후 종료( 대기 했다 하더라도 get 은 불가능,
//                                                     // cancel 이 되면 isDone 은 무조건 true 가 된다.
//        System.out.println(helloFuture.isDone());
//        System.out.println("End!");
//        executorService.shutdown();



        /**
         * Executors
         */

//        // newSingleThreadScheduledExecutor
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        executorService.schedule(getRunnable("Hello"), 3, TimeUnit.SECONDS); // 3초 후 실행
//        executorService.scheduleAtFixedRate(getRunnable("World"), 1, 2, TimeUnit.SECONDS); // 1초 후 2초마다 실행


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
