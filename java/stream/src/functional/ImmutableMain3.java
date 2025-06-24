package functional;

import java.util.List;

public class ImmutableMain3 {

    public static void main(String[] args) {
        ImmutablePerson i1 = new ImmutablePerson("Kim", 10);
        ImmutablePerson i2 = new ImmutablePerson("Lee", 20);

        List<ImmutablePerson> originList = List.of(i1, i2);
        System.out.println("originList = " + originList);
        List<ImmutablePerson> resultList = originList.stream()
                .map(p -> p.withAge(p.getAge() + 1))
                .toList();

        System.out.println("=== 실행 후 ===");
        System.out.println("originList = " + originList);
        System.out.println("resultList = " + resultList);
    }
    /*
        originList = [ImmutablePerson{name='Kim', age=10}, ImmutablePerson{name='Lee', age=20}]
        === 실행 후 ===
        originList = [ImmutablePerson{name='Kim', age=10}, ImmutablePerson{name='Lee', age=20}]
        resultList = [ImmutablePerson{name='Kim', age=11}, ImmutablePerson{name='Lee', age=21}]
     */
}
