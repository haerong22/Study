package methodref;

import java.util.function.BiFunction;

public class MethodRefEx6 {

    public static void main(String[] args) {

        Person person = new Person("Kim");

        // 람다
        BiFunction<Person, Integer, String> func1 = (Person p, Integer number) -> p.introduceWithNumber(number);

        System.out.println("person.introduceWithNumber = " + func1.apply(person, 1));

        // 매서드 참조
        // 타입이 첫 번째 매개변수가 됨, 첫 번째 매개변수의 메서드 호출
        // 나머지는 순서대로 전달
        BiFunction<Person, Integer, String> func2 = Person::introduceWithNumber;
        System.out.println("person.introduceWithNumber = " + func2.apply(person, 1));
    }

    /*
        person.introduceWithNumber = I am Kim, my number is 1
        person.introduceWithNumber = I am Kim, my number is 1
     */

}
