package annotation.java;

public class OverrideMain {

    static class A {
        public void call() {
            System.out.println("A.call");
        }
    }

    static class B extends A{

        @Override
        public void call() {
            System.out.println("B.call");
        }
    }

    public static void main(String[] args) {
        A a = new B();
        a.call();
    }
}
