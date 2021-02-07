package functional;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

// 변수 캡쳐
public class VariableCapture {

    private void run() {
        // effective final : 다른 곳에서 변수를 변경하지 않는 경우 (사실상 final)
        // 로컬 클래스, 익명 클래스, 람다 에서 사용가능
        // 로컬 클래스, 익명 클래스 는 shadowing 가능
        // 람다는 shadowing 이 일어나지 않는다. (람다를 감싸고 있는 메소드와 스코프가 같다)
        int baseNumber = 10;

        // 로컬 클래스
        class LocalClass {
            void printBaseNumber() {
                int baseNumber = 11;
                System.out.println(baseNumber);
            }
        }

        // 익명 클래스
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer baseNumber) {
                System.out.println(baseNumber);
            }
        };

       // 람다
       IntConsumer printInt = i -> {
            System.out.println(i + baseNumber);
        };

        printInt.accept(10);
    }
}
