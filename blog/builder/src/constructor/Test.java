package constructor;

public class Test {
    public static void main(String[] args) {

        Member member1 = new Member("email@naver.com", "1234", "kim");
        Member member2 = new Member("email@naver.com", "1234", "kim", "seoul", "01012341234");

        System.out.println("member1 = " + member1);
        System.out.println("member2 = " + member2);
    }
}
