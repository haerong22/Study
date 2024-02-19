package functional;

/**
 * 함수형 인터페이스 : 인터페이스 안에 추상메소드가 한개 인 것
 * static, default 메소드는 있더라도 상관 없음! 중요한것은 추상메소드가 한개 존재
 * @FunctionalInterface 어노테이션을 이용하여 함수형 인터페이스를 명시적으로 선언할 수 있다.
 * 입력값이 동일한 경우 결과값이 항상 같아야 한다.
 * -> 결과값이 달라질 여지가 있는 경우? 함수 밖에 있는 값을 사용하거나 변경하는 경우
 * -> 외부값을 참조만 하는 것은 가능하나 final 임을 가정한다. but lambda 표현식은 사용 불가
 */

@FunctionalInterface
public interface RunSomething {

    int doIt(int num);

    static void staticMethod() {
        System.out.println("인터페이스 안에 static 메소드");
    }

    default void defaultMethod() {
        System.out.println("인터페이스 안에 default 메소드드");
    }
}
