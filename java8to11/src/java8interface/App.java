package java8interface;

public class App {

    public static void main(String[] args) {
        Foo foo = new Default("kim");

        foo.printName();
        foo.printNameUpperCase();

        Foo.printHello();
    }
}
