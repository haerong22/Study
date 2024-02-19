package poly;

// 추상 클래스 -> 불완전 클래스 : 객체 생성 불가  new Animal() (X)
public abstract class Animal {

    public abstract void eat(); // 추상 메소드 -> 불완전 메소드

    public void move() { // 일반 메소드도 작성 가능
        System.out.println("이동한다.");
    }
}
