package stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 스트림은 데이터를 담고 있는 저장소(컬렉션)이 아니다.
 * 데이터를 변경 X
 * 스트림은 재사용 X
 * 중계 오퍼레이션 -> Lazy
 */

public class App {
    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        System.out.println("spring 으로 시작하는 수업");
        springClasses.stream()
                .filter(c -> c.getTitle().startsWith("spring"))
                .forEach(c -> System.out.println(c.getTitle()));
        System.out.println("===============================================");

        System.out.println("close 되지 않은 수업");
        springClasses.stream()
                .filter(Predicate.not(OnlineClass::isClosed))
                .forEach(c -> System.out.println(c.getTitle()));
        System.out.println("===============================================");

        System.out.println("수업 이름만 모아서 스트림 만들기");
        springClasses.stream()
                .map(OnlineClass::getTitle)
                .forEach(System.out::println);

        List<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "The Java, Test", true));
        javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
        javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

        List<List<OnlineClass>> events = new ArrayList<>();
        events.add(springClasses);
        events.add(javaClasses);

        System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
        events.stream()
                .flatMap(Collection::stream)
                .forEach(c -> System.out.println(c.getId()));
        System.out.println("===============================================");

        System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
        Stream.iterate(10, i -> i + 1)
                .skip(10)
                .limit(10)
                .forEach(System.out::println);
        System.out.println("===============================================");

        System.out.println("자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
        boolean test = javaClasses.stream()
                .anyMatch(oc -> oc.getTitle().contains("Test"));
        System.out.println(test);
        System.out.println("===============================================");

        System.out.println("스프링 수업 중에 제목이 spring 이 들어간 제목만 모아서 List 로 만들기");
        List<String> spring = springClasses.stream()
                .map(OnlineClass::getTitle)
                .filter(title -> title.contains("spring"))
                .collect(Collectors.toList());
        spring.forEach(System.out::println);
        System.out.println("===============================================");


//        List<String> names = new ArrayList<>();
//        names.add("kim");
//        names.add("lee");
//        names.add("park");
//        names.add("choi");
//
//        // 쉬운 병렬처리
//        List<String> collect = names.parallelStream().map(s -> {
//            System.out.println(s + " " + Thread.currentThread().getName());
//            return s.toUpperCase();
//        }).collect(Collectors.toList());
//        collect.forEach(System.out::println);

//        // 중계형 오퍼레이터는 Lazy -> 종료형 오퍼레이터가 오기 전까지 실행 X
//        names.stream().map((s) -> {
//            System.out.println(s);
//            return s.toUpperCase();
//        });

    }
}
