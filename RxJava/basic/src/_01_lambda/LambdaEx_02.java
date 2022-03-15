package _01_lambda;

public class LambdaEx_02 {

    public static void main(String[] args) {

        // 람다 사용 X
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Run Thread");
            }
        }).start();

        // 람다 사용
        new Thread(() -> System.out.println("Lambda Run Thread")).start();
    }
}
