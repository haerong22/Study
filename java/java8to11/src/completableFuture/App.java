package completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class App {

    private static Runnable getRunnable(String message) {
        return () -> System.out.println(message + ": " + Thread.currentThread().getName());
    }

    private static CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("World: " + Thread.currentThread().getName());
            return message + " World";
        });
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        /**
         * CompletableFuture
         */

        // exception
        boolean throwError = true;

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            if (throwError) {
                throw new IllegalArgumentException();
            }
            System.out.println("Hello: " + Thread.currentThread().getName());
            return "Hello";
        }).handle((result, error) -> {
            if (error != null) {
                System.out.println(error);
                return "Error!";
            }
            return result;
        });

        System.out.println(hello.get());

//        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
//            if (throwError) {
//                throw new IllegalArgumentException();
//            }
//            System.out.println("Hello: " + Thread.currentThread().getName());
//            return "Hello";
//        }).exceptionally(ex -> {
//            System.out.println(ex);
//            return "Error!";
//        });
//
//        System.out.println(hello.get());



//        // Compose - 연관관계가 없을 때
//        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Hello: " + Thread.currentThread().getName());
//            return "Hello";
//        });
//
//        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
//            System.out.println("World: " + Thread.currentThread().getName());
//            return "World";
//        });
//
//        CompletableFuture<String> future = hello.thenCombine(world, (h, w) -> {
//            return h + " " + world;
//        });
//
//        System.out.println(future.get());
//
//        // allOf : 2개 이상일 때 모든 결과를 받아서 처리
//        List<CompletableFuture<String>> futures = Arrays.asList(hello, world);
//        CompletableFuture<Void> futureArray = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
//
//        CompletableFuture<List<String>> results = CompletableFuture.allOf(futureArray)
//                .thenApply(v -> {
//                    return futures.stream()
//                            .map(CompletableFuture::join)
//                            .collect(Collectors.toList());
//                });
//
//        results.get().forEach(System.out::println);
//
//        // anyOf : 결과 하나
//        CompletableFuture<Void> anyFuture = CompletableFuture.anyOf(hello, world).thenAccept(System.out::println);
//        future.get();


//        // Compose - 연관관계가 있을 때
//        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Hello: " + Thread.currentThread().getName());
//            return "Hello";
//        });
//
//        CompletableFuture<String> future = hello.thenCompose(App::getWorld);
//
//        System.out.println(future.get());


//        // ThreadPool 을 직접 생성하여 작업
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Hello: " + Thread.currentThread().getName());
//            return "world";
//        }, executorService).thenAcceptAsync(s -> {
//            System.out.println(Thread.currentThread().getName());
//            System.out.println(s.toUpperCase());
//        }, executorService);
//
//        future.get();
//        executorService.shutdown();


//        // Callback - 리턴 값을 받아서 수행 만
//        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Hello: " + Thread.currentThread().getName());
//            return "world";
//        }).thenRun(() -> {
//            System.out.println(Thread.currentThread().getName());
//        });
//
//        future.get();


//        // Callback - 리턴 값을 받아서 수행 후 리턴 타입 X
//        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Hello: " + Thread.currentThread().getName());
//            return "world";
//        }).thenAccept(s -> {
//            System.out.println(Thread.currentThread().getName());
//            System.out.println(s.toUpperCase());
//        });
//
//        future.get();


//        // Callback - 리턴 값을 받아서 수행 후 리턴 타입 O
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Hello: " + Thread.currentThread().getName());
//            return "world";
//        }).thenApply(s -> {
//            System.out.println(Thread.currentThread().getName());
//            return s.toUpperCase();
//        });
//
//        System.out.println(future.get());


//        CompletableFuture<String> future = new CompletableFuture<>();
//        future.complete("hello");
//        System.out.println(future.get());
//
//        System.out.println("===========================================");
//
//        CompletableFuture<String> future1 = CompletableFuture.completedFuture("world");
//        System.out.println(future1.get());
//
//        System.out.println("===========================================");
//
//        // 리턴 타입 X
//        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
//            System.out.println("Hello: " + Thread.currentThread().getName());
//        });
//        future2.get();
//
//        System.out.println("===========================================");
//
//        // 리턴 타입 O
//        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Hello: " + Thread.currentThread().getName());
//            return "world";
//        });
//        System.out.println(future3.get());



        /**
         * Callable, Future
         */

//        // invokeAll, invokeAny
////        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//
//        Callable<String> hello = () -> {
//            Thread.sleep(2000L);
//            return "Hello";
//        };
//
//        Callable<String> world = () -> {
//            Thread.sleep(3000L);
//            return "world";
//        };
//
//        Callable<String> java = () -> {
//            Thread.sleep(1000L);
//            return "java";
//        };
//
//        // invokeAny
//        String future = executorService.invokeAny(Arrays.asList(hello, world, java)); // 가장 먼저 도착한 값
//        System.out.println(future);
//
//        // invokeAll
//        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, world, java)); // 다 끝날때 까지 대기
//        for (Future<String> f : futures) {
//            System.out.println(f.get());
//        }
//
//        executorService.shutdown();


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
