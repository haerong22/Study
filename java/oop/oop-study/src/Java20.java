import myObject.Animal;
import myObject.Cat;
import myObject.Dog;

public class Java20 {
    public static void main(String[] args) {

        // upcasting
        Animal cat = new Cat();
        Animal dog = new Dog();

        // 오버라이딩 한 메소드 사용
        // cat 에만 있는 메소드는 사용 하지못함 -> cat.say() X
        cat.eat(); // 컴파일 시점 -> Animal 의 eat(), 실행 시점 -> Cat 의 eat()
        dog.eat(); // 컴파일 시점 -> Animal 의 eat(), 실행 시점 -> Dog 의 eat()


        // downcasting
        // Cat c = (Cat) ani;
        // c.night();
        ((Cat) cat).say(); // . 연산자가 casting 연산자 보다 우선순위가 높다.
    }
}
