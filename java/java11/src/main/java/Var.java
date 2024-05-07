import java.util.function.Consumer;

public class Var {

    public static void main(StringClass[] args) {

        // String 타입을 명시적으로 작성
        Consumer<String> c1 = (String x) -> System.out.println(x);

        // 람다식 매개변수에 var 사용
        Consumer<String> c2 = (var x) -> System.out.println(x);

    }
}
