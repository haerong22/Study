package setter;

public class Test {

    public static void main(String[] args) {

        Member member = new Member();
        member.setEmail("email@naver.com");
        member.setPassword("1234");
        member.setName("kim");

        System.out.println("member = " + member);
    }
}
