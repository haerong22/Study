import myObject.MemberVO;

public class Java15 {
    public static void main(String[] args) {

        MemberVO m = new MemberVO("홍길동", 20, "010-1123-1123", "서울");

        System.out.println(m.toString());
        System.out.println(m); // toString 생략 가능
    }
}
