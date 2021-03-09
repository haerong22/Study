import myObject.MemberVO;

public class Java14 {

    public static void main(String[] args) {

        MemberVO m = new MemberVO();

        m.setName("홍길동");
        m.setAge(20);
        m.setTel("010-1234-1234");
        m.setAddr("서울");

        System.out.println(m.getName());
        System.out.println(m.getAge());
        System.out.println(m.getTel());
        System.out.println(m.getAddr());
    }
}
