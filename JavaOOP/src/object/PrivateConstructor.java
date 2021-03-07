package object;

public class PrivateConstructor {

    private PrivateConstructor() {
    }

    public void nonStaticMethod() {
        System.out.println("non-static-method");
    }

    public static void staticMethod() {
        System.out.println("static method");
    }
}
