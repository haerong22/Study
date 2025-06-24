package functional;

import java.util.List;

public class ImmutableMain2 {

    public static void main(String[] args) {
        MutablePerson m1 = new MutablePerson("Kim", 10);
        MutablePerson m2 = new MutablePerson("Lee", 20);

        List<MutablePerson> originList = List.of(m1, m2);
        System.out.println("originList = " + originList);
        List<MutablePerson> resultList = originList.stream()
                .map(p -> {
                    p.setAge(p.getAge() + 1);
                    return p;
                })
                .toList();

        System.out.println("=== 실행 후 ===");
        System.out.println("originList = " + originList);
        System.out.println("resultList = " + resultList);
    }
    /*
        originList = [MutablePerson{name='Kim', age=10}, MutablePerson{name='Lee', age=20}]
        === 실행 후 ===
        originList = [MutablePerson{name='Kim', age=11}, MutablePerson{name='Lee', age=21}]
        resultList = [MutablePerson{name='Kim', age=11}, MutablePerson{name='Lee', age=21}]
     */
}
