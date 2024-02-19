package optional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {

    private static OnlineClass createNewClass() {
        System.out.println("create new class");
        return new OnlineClass(10, "New class", false);
    }
    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        Optional<OnlineClass> optional = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

        // flatMap
        Optional<Progress> progress = optional.flatMap(OnlineClass::getProgress);
        Optional<Optional<Progress>> progress1 = optional.map(OnlineClass::getProgress);
        Optional<Progress> progress2 = progress1.orElse(Optional.empty());

//        // map
//        Optional<Optional<Progress>> progress = optional.map(OnlineClass::getProgress);
//        Optional<Progress> progress1 = progress.orElseThrow();
//        Progress progress2 = progress1.orElseThrow();
//        System.out.println(progress2);

//        Optional<Integer> integer = optional.map(OnlineClass::getId);
//        System.out.println(integer.isPresent());

//        //filter
//        Optional<OnlineClass> onlineClass = optional.filter(OnlineClass::isClosed);
//        System.out.println(onlineClass.isPresent());

//        // orElseThrow
//        OnlineClass onlineClass = optional.orElseThrow(IllegalArgumentException::new); // 없으면 error
//        System.out.println(onlineClass.getTitle());

//        // orElseGet
//        OnlineClass onlineClass = optional.orElseGet(App::createNewClass); // 있으면 실행 안함
//        System.out.println(onlineClass.getTitle());

//        // orElse
//        OnlineClass onlineClass = optional.orElse(createNewClass()); // 있으면 꺼내고 없으면 ~~ 리턴 -> 있어도 ~~ 를 실행한다.
//        System.out.println(onlineClass.getTitle());

//        // ifPresent, get
//        optional.ifPresent(oc -> System.out.println(oc.getTitle())); // 있으면 ~~ 실행

//        boolean present = optional.isPresent();
//        System.out.println(present);
//        OnlineClass onlineClass = optional.get(); // optional 의 내용 꺼내기
//        System.out.println(onlineClass.getTitle());

//        // null 체크는 실수 가능성이 높다
//        // 또한 null 을 리턴하는 자체가 문제
//        OnlineClass spring_boot = new OnlineClass(1, "spring boot", true);
//        Progress progress = spring_boot.getProgress();
//        if (progress != null) {
//            System.out.println(progress.getStudyDuration());
//        }
    }
}
