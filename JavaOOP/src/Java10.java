import object.BookVO;

public class Java10 {
    public static void main(String[] args) {

        /**
         * 책 (BookVO) 이라는 자료형 만들기 -> class 를 통해 만든다.
         * class 를 만들면 디폴트 생성자가 자동 생성된다. (생략)
         * 생성자는 객체를 생성하는 작업을 한다. (기계어코드)
         * 객체를 생성하면 이 객체를 가리키는 this 객체도 자동 생성된다.
         */
        BookVO b = new BookVO(); // 객체 생성
        b.title = "자바";
        b.price = 20000;
        b.company = "출판사";
        b.page = 670;

        System.out.println(b.title);
        System.out.println(b.price);
        System.out.println(b.company);
        System.out.println(b.page);
    }
}
