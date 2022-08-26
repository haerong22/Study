import poly.Animal;
import poly.Cat;
import poly.Dog;

public class Java23 {
    public static void main(String[] args) {
        // 추상클래스
        // Animal animal = new Animal; (X)
        // upcasting 으로 활용
        Animal cat = new Cat();
        Animal dog = new Dog();

        cat.eat();
        cat.move();

        dog.eat();
        dog.move();
    }
}
