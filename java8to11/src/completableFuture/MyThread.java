package completableFuture;

public class MyThread extends Thread{

    @Override
    public void run() {
        System.out.println("Thread: " + Thread.currentThread().getName());
    }
}
