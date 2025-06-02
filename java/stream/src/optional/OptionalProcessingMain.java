package optional;

import java.util.Optional;

public class OptionalProcessingMain {

    public static void main(String[] args) {
        Optional<String> optValue = Optional.of("Hello");
        Optional<String> optEmpty = Optional.empty();

        // 값이 있으면 Consumer 실행, 없으면 아무것도 안함
        System.out.println("=== ifPresent() ===");
        optValue.ifPresent(v -> System.out.println("optValue 값: " + v));
        optEmpty.ifPresent(v -> System.out.println("optEmpty 값: " + v)); // 실행 X

        System.out.println();

        // 값이 있으면 Consumer 실행, 없으면 Runnable 실행
        System.out.println("=== ifPresentOrElse() ===");
        optValue.ifPresentOrElse(
                v -> System.out.println("optValue 값: " + v),
                () -> System.out.println("optValue 비어있음")
        );
        optValue.ifPresentOrElse(
                v -> System.out.println("optEmpty 값: " + v),
                () -> System.out.println("optEmpty 비어있음")
        );

        System.out.println();

        // 값이 있으면 Function 적용 후 Optional 반환, 없으면 Optional.empty()
        System.out.println("=== map() ===");
        Optional<Integer> lengthOpt1 = optValue.map(String::length);
        System.out.println("lengthOpt1 = " + lengthOpt1);
        Optional<Integer> lengthOpt2 = optEmpty.map(String::length);
        System.out.println("lengthOpt2 = " + lengthOpt2);

        System.out.println();

        // 이미 Optional 을 반환하는 경우 중첩 제거
        System.out.println("=== flatMap() ===");
        System.out.println("[map]");
        Optional<Optional<String>> nestedOpt = optValue.map(s -> Optional.of(s));
        System.out.println("optValue = " + optValue);
        System.out.println("nestedOpt = " + nestedOpt);

        System.out.println("[flatMap]");
        Optional<String> flattenedOpt = optValue.flatMap(s -> Optional.of(s));
        System.out.println("flattenedOpt = " + flattenedOpt);

        System.out.println();

        // 값이 있고 조건을 만족하면 반환, 불만족시 Optional.empty()
        System.out.println("=== filter() ===");
        Optional<String> filtered1 = optValue.filter(s -> s.startsWith("H"));
        Optional<String> filtered2 = optValue.filter(s -> s.startsWith("X"));
        System.out.println("filtered1 = " + filtered1);
        System.out.println("filtered2 = " + filtered2);

        System.out.println();

        // 값이 있으면 단일 요소 스트림, 없으면 빈 스트림
        System.out.println("=== stream() ===");
        optValue.stream()
                .forEach(s -> System.out.println("optValue.stream() -> " + s));

        // 값이 없으면 실행 X
        optEmpty.stream()
                .forEach(s -> System.out.println("optEmpty.stream() -> " + s));

    }

    /*
        === ifPresent() ===
        optValue 값: Hello

        === ifPresentOrElse() ===
        optValue 값: Hello
        optEmpty 값: Hello

        === map() ===
        lengthOpt1 = Optional[5]
        lengthOpt2 = Optional.empty

        === flatMap() ===
        [map]
        optValue = Optional[Hello]
        nestedOpt = Optional[Optional[Hello]]
        [flatMap]
        flattenedOpt = Optional[Hello]

        === filter() ===
        filtered1 = Optional[Hello]
        filtered2 = Optional.empty

        === stream() ===
        optValue.stream() -> Hello
     */
}
