package functional;

import java.util.function.Function;

/**
 * Java 가 기본으로 제공하는 함수형 인터페이스
 * java.lang.function 패키지
 *
 * apply : 값을 받아서 값을 리턴하는 메소드
 */

public class Plus10 implements Function<Integer, Integer> {
    @Override
    public Integer apply(Integer integer) {
        return integer + 10;
    }
}
