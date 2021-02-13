package stream;

import java.util.ArrayList;
import java.util.List;

/**
 * 스트림은 데이터를 담고 있는 저장소(컬렉션)이 아니다.
 * 데이터를 변경 X
 * 스트림은 재사용 X
 * 중계 오퍼레이션 -> Lazy
 */

public class App {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("kim");
        names.add("lee");
        names.add("park");
        names.add("choi");

        // 중계형 오퍼레이터는 Lazy -> 종료형 오퍼레이터가 오기 전까지 실행 X
        names.stream().map((s) -> {
            System.out.println(s);
            return s.toUpperCase();
        });

        System.out.println("======================");

    }
}
