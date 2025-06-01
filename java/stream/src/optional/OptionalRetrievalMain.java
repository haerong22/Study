package optional;

import java.util.Optional;

public class OptionalRetrievalMain {

    public static void main(String[] args) {
        Optional<String> optValue = Optional.of("Hello");
        Optional<String> optEmpty = Optional.empty();

        // isPresent() : 값이 있으면 true
        System.out.println("=== isPresent() / isEmpty() ===");
        System.out.println("optValue.isPresent() = " + optValue.isPresent());
        System.out.println("optValue.isEmpty() = " + optValue.isEmpty());
        System.out.println("optEmpty.isPresent() = " + optEmpty.isPresent());
        System.out.println("optEmpty.isEmpty() = " + optEmpty.isEmpty());

        System.out.println();

        // get() : 내부 값 조회, 값이 없으면 NoSuchElementException
        System.out.println("=== get() ===");
        String getValue = optValue.get();
        System.out.println("getValue = " + getValue);
//        String getValue2 = optEmpty.get(); // NoSuchElementException

        System.out.println();

        // orElse() : 값이 있으면 그 값, 없으면 지정된 기본값
        System.out.println("=== orElse() ===");
        String value1 = optValue.orElse("기본값");
        String empty1 = optEmpty.orElse("기본값");
        System.out.println("value1 = " + value1);
        System.out.println("empty1 = " + empty1);

        System.out.println();

        // orElseGet() : 값이 없을 때 supplier 실행되어 기본값 생성
        System.out.println("=== orElseGet() ===");
        String value2 = optValue.orElseGet(() -> {
            System.out.println("람다 호출 - optValue");
            return "New Value";
        });
        String empty2 = optEmpty.orElseGet(() -> {
            System.out.println("람다 호출 - optEmpty");
            return "New Value";
        });
        System.out.println("value2 = " + value2);
        System.out.println("empty2 = " + empty2);

        System.out.println();

        // orElseThrow() 값이 있으면 반환, 없으면 예외 발생
        System.out.println("=== orElseThrow() ===");
        String value3 = optValue.orElseThrow(() -> new RuntimeException("값이 없습니다."));
        System.out.println("value3 = " + value3);

        try {
            String empty3 = optEmpty.orElseThrow(() -> new RuntimeException("값이 없습니다."));
            System.out.println("empty3 = " + empty3);
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }

        System.out.println();

        // or() : Optional 반환
        System.out.println("=== or() ===");
        Optional<String> result1 = optValue.or(() -> Optional.of("Fallback"));
        System.out.println("result1 = " + result1);

        Optional<String> result2 = optEmpty.or(() -> Optional.of("Fallback"));
        System.out.println("result2 = " + result2);
    }

    /*
        === isPresent() / isEmpty() ===
        optValue.isPresent() = true
        optValue.isEmpty() = false
        optEmpty.isPresent() = false
        optEmpty.isEmpty() = true

        === get() ===
        getValue = Hello

        === orElse() ===
        value1 = Hello
        empty1 = 기본값

        === orElseGet() ===
        람다 호출 - optEmpty
        value2 = Hello
        empty2 = New Value

        === orElseThrow() ===
        value3 = Hello
        예외 발생: 값이 없습니다.

        === or() ===
        result1 = Optional[Hello]
        result2 = Optional[Fallback]
     */
}
