import myObject.Animal;
import myObject.Cat;
import myObject.Dog;

public class Java19 {
    public static void main(String[] args) {
        Animal dog = new Dog(); // 상속 관계 : 자동 형변환 -> Object casting  부모 클래스 = 자식클래스 (Up casting)
        Animal cat = new Cat();

        dog.eat();
        cat.eat();
    }
}
