package random;

import java.util.random.RandomGeneratorFactory;

public class RandomGeneratorFac {

    public static void main(String[] args) {
        RandomGeneratorFactory.all()
                .forEach(r -> System.out.println(r.name()));
    }
}
