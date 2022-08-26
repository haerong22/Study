import myObject.Animal;
import myObject.Cat;
import myObject.Dog;

public class Java21 {
    public static void main(String[] args) {
        // 1. 다형성 인수
        Dog dog = new Dog();
        Cat cat = new Cat();

        display(dog);
        display(cat);
    }

    public static void display(Animal animal) { // 다형성 인수 upcasting
        animal.eat();
        // Cat 타입 인지 확인 instanceof -> Cat 타입 일때만 실행
        if (animal instanceof Cat) {
            ((Cat) animal).say(); // downcasting
        }
    }
}
