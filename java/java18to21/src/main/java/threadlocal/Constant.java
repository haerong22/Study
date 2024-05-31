package threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Constant {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 5; i++) {
            executor.submit(Constant::initNumber).get();
        }

        for (int i = 1; i <= 5 ; i++) {
            executor.submit(Constant::printNumber);
        }

        executor.close();
    }

    private static final ThreadLocal<Integer> randomNumber = new ThreadLocal<>();

    private static void initNumber() {
        if (randomNumber.get() == null) {
            int num = ThreadLocalRandom.current().nextInt(10);
            printWithThread("값 설정 완료 : " + num);
            randomNumber.set(num);
        }
    }

    private static void printNumber() {
        printWithThread("값 확인 : " + randomNumber.get());
    }

    private static void printWithThread(String str) {
        System.out.printf("[%s] - %s\n", Thread.currentThread().getName(), str);
    }
}
