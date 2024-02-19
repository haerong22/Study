package java8interface;

public class Default implements Foo, Bar{

    String name;

    public Default(String name) {
        this.name = name;
    }

    @Override
    public void printName() {
        System.out.println(this.name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void printNameUpperCase() {
        System.out.println(this.name.toUpperCase());
    }
}
