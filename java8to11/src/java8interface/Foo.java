package java8interface;

/**
 * default Method
 * 메소드 선언이 아닌 구현체 제공
 * 어떤 인스턴스에서 항상 정상적으로 동작할 지에 대한 보장이 없기 때문에 반드시 문서화 해야 한다.
 * 구현체에서 오버라이딩 가능
 * Object 에서 제공하는 메소드는 재정의 불가
 * 인터페이스를 상속받는 인터페이스에서 다시 추상 메소드로 변경 가능
 * 같은 default 메소드를 가진 두 인터페이스를 모두 상속 받았을 때는 직접 오버라이딩
 *
 * static Method
 */
public interface Foo {

    void printName();

    /**
     * @implSpec
     * 이 구현체는 getName() 으로 가져온 문자열을 대문자로 바꿔 출력한다.
     */
    default void printNameUpperCase() {
        System.out.println(getName().toUpperCase());
    }

    static void printHello() {
        System.out.println("Hello");
    }

    String getName();
}
