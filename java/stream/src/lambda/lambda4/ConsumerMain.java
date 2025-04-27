package lambda.lambda4;

import java.util.function.Consumer;

public class ConsumerMain {

    public static void main(String[] args) {

        Consumer<String> consumer1 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer1.accept("hello consumer");

        Consumer<String> consumer2 = s -> System.out.println(s);
        consumer2.accept("hello consumer");
    }

    /*
        hello consumer
        hello consumer
     */
}
