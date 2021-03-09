import myObject.BookDTO;

public class Java13 {
    public static void main(String[] args) {
        // 책 -> class(BookDTO) -> 객체 -> 인스턴스
        String title = "자바";
        int price = 25000;
        String company = "출판사";
        int page = 890;

        // 데이터 이동을 쉽게 하기 위한 DTO
        BookDTO dto; // dto (Object : 객체)
        dto = new BookDTO(title, price, company, page); // dto (Instance : 인스턴스)

        // 데이터 이동
        bookPrint(dto);
    }

    public static void bookPrint(BookDTO dto) {
        System.out.println(dto.title);
        System.out.println(dto.price);
        System.out.println(dto.company);
        System.out.println(dto.page);
    }
}
