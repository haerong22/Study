package threadlocal;

import java.util.Random;
import java.util.random.RandomGenerator;

public class ConstantV2 {

    static final ScopedValue<Integer> randomNumber = ScopedValue.newInstance();

    public static void main(String[] args) {
        final RandomGenerator generator = new Random();

        ScopedValue.where(ConstantV2.randomNumber, generator.nextInt(10))
                .run(() -> {
                    System.out.println("test1 :" + randomNumber.get());
                    System.out.println("test1 :" + randomNumber.get());
                });

    }
}
