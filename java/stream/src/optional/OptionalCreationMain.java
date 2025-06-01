package optional;

import java.util.Optional;

public class OptionalCreationMain {

    public static void main(String[] args) {

        // of() : 값이 null 이 아님, null 이면 NullPointerException 발생
        String nonNullValue = "Hello Optional!";
        Optional<String> opt1 = Optional.of(nonNullValue);
        System.out.println("opt1 = " + opt1);

        // ofNullable() : 값이 null 일수도 아닐수도 있음
        Optional<String> opt2 = Optional.ofNullable("Hello");
        Optional<Object> opt3 = Optional.ofNullable(null);
        System.out.println("opt2 = " + opt2);
        System.out.println("opt3 = " + opt3);

        // empty() : 비어있는 optional
        Optional<Object> opt4 = Optional.empty();
        System.out.println("opt4 = " + opt4);
    }

    /*
        opt1 = Optional[Hello Optional!]
        opt2 = Optional[Hello]
        opt3 = Optional.empty
        opt4 = Optional.empty
     */
}
