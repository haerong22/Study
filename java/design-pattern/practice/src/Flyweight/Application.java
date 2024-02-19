package Flyweight;

public class Application {
    public static void main(String[] args) {

        FlyweightFactory factory = new FlyweightFactory();

        Flyweight flyweight = factory.getFlyweight("A");
        System.out.println(flyweight);
        flyweight = factory.getFlyweight("A");
        System.out.println(flyweight);
        flyweight = factory.getFlyweight("A");
        System.out.println(flyweight);
        flyweight = factory.getFlyweight("B");
        System.out.println(flyweight);
        flyweight = factory.getFlyweight("B");
        System.out.println(flyweight);
    }
}
