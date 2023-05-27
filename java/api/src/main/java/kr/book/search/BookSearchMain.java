package kr.book.search;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class BookSearchMain {

    public static void main(String[] args) {
        final String apiKey = args[0];
        KakaoBookApi.setApiKey(apiKey);

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("도서 제목을 입력하세요: ");
            String title = scanner.nextLine();

            List<Book> books = KakaoBookApi.searchBooks(title);

            if (books.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
            } else {
                String filename = "도서목록.pdf";
                PdfGenerator.generateBookListPdf(books, filename);
                System.out.println(filename + " 파일이 생성되었습니다.");
            }
        } catch (IOException e) {
            System.err.println("에러가 발생하였습니다: " + e.getMessage());
        }
    }
}
