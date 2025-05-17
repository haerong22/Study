package methodref;

import java.util.function.Function;

public class MethodRefEx3 {

    public static void main(String[] args) {

        // 임의 객체의 인스턴스 메서드 참조
        Person person1 = new Person("Kim");
        Person person2 = new Person("Park");
        Person person3 = new Person("Lee");

        // 람다
        Function<Person, String> func1 = (Person person) -> person.introduce();
        System.out.println("person1.introduce = " + func1.apply(person1));
        System.out.println("person2.introduce = " + func1.apply(person2));
        System.out.println("person3.introduce = " + func1.apply(person3));

        // 메서드 참조, 타입이 첫 번째 매개변수가 됨
        // 첫 번째 매개변수의 메서드를 호출, 나머지는 순서대로 매개변수에 전달
        Function<Person, String> func2 =  Person::introduce; // 타입::인스턴스메서드
        System.out.println("person1.introduce = " + func2.apply(person1));
        System.out.println("person2.introduce = " + func2.apply(person2));
        System.out.println("person3.introduce = " + func2.apply(person3));
    }

    /*
        person1.introduce = I am Kim
        person2.introduce = I am Park
        person3.introduce = I am Lee

        person1.introduce = I am Kim
        person2.introduce = I am Park
        person3.introduce = I am Lee
     */

}
