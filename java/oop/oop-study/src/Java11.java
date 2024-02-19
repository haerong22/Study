import myObject.BookVO;

public class Java11 {

    public static void main(String[] args) {
        // 생성자 -> 생성 + 초기화 -> 중복정의
        BookVO b = new BookVO("자바",20000,"길벗",790);

        System.out.println(b.title);
        System.out.println(b.price);
        System.out.println(b.company);
        System.out.println(b.page);
    }
}
