package completableFuture;

public class App {
    public static void main(String[] args) throws InterruptedException {

        /**
         * Thread 사용
         */

        // join
        Thread thread = new Thread(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
        thread.start();

        System.out.println("Hello: " + Thread.currentThread().getName());
        thread.join();
        System.out.println(thread + " is finished");


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
