package methodref;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MethodRefEx4 {

    public static void main(String[] args) {
        List<Person> personList = List.of(
                new Person("Kim"),
                new Person("Park"),
                new Person("Lee")
        );

        List<String> result1 = mapPersonToString(personList, (Person p) -> p.introduce());
        List<String> result2 = mapPersonToString(personList, Person::introduce);
        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);

        List<String> upperResult1 = mapStringToString(result1, (String s) -> s.toUpperCase());
        List<String> upperResult2 = mapStringToString(result1, String::toUpperCase);
        System.out.println("upperResult1 = " + upperResult1);
        System.out.println("upperResult2 = " + upperResult2);
    }

    /*
        result1 = [I am Kim, I am Park, I am Lee]
        result2 = [I am Kim, I am Park, I am Lee]
        upperResult1 = [I AM KIM, I AM PARK, I AM LEE]
        upperResult2 = [I AM KIM, I AM PARK, I AM LEE]
     */

    static List<String> mapPersonToString(List<Person> personList, Function<Person, String> func) {
        List<String> result = new ArrayList<>();
        for (Person p : personList) {
            String applied = func.apply(p);
            result.add(applied);
        }
        return result;
    }

    static List<String> mapStringToString(List<String> strings, Function<String, String> func) {
        List<String> result = new ArrayList<>();
        for (String s : strings) {
            String applied = func.apply(s);
            result.add(applied);
        }
        return result;
    }

}
