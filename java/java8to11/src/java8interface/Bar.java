package java8interface;

public interface Bar {

    default void printNameUpperCase() {
        System.out.println("BAR");
    }
}
