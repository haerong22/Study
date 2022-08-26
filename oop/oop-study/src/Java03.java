import myObject.Book;
import myObject.Person;

public class Java03 {

    public static void main(String[] args) {
        // 관계를 이해하라. PDT vs UDDT

        // 정수 1개를 저장하기 위한 변수를 선언하시오.
        int a = 10;

        // 책 1권을 저장하기 위한 선언하시오.
        Book b = new Book();
        b.title = "자바";
        b.price = 15000;
        b.company = "한빛미디어";
        b.page = 700;

        System.out.println(b.title);
        System.out.println(b.price);
        System.out.println(b.company);
        System.out.println(b.page);

        Person p = new Person();
        p.name = "홍길동";
        p.age = 20;
        p.weight = 78.9f;
        p.height = 184.8f;

        System.out.println(p.name);
        System.out.println(p.age);
        System.out.println(p.weight);
        System.out.println(p.height);
    }
}
