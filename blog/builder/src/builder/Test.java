package builder;

public class Test {

    public static void main(String[] args) {

        Member member = Member.builder()
                .email("email@naver.com")
                .name("kim")
                .password("1234")
                .build();

        System.out.println("member = " + member);
    }
}
