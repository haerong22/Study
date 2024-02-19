import myObject.Animal;
import myObject.Cat;
import myObject.Dog;

public class Java22 {
    public static void main(String[] args) {
        // 2. 다형성 배열
        Animal[] animals = new Animal[2];
        animals[0] = new Dog();
        animals[1] = new Cat();

        for (Animal animal : animals) {
            animal.eat();
            if (animal instanceof Cat) {
                ((Cat) animal).say();
            }
        }
    }
}
