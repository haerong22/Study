package itext;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class BookInfoToPDF {

    public static void main(String[] args) {
        Map<String, String> bookInfo = new HashMap<>();
        bookInfo.put("title", "자바");
        bookInfo.put("author", "홍길동");
        bookInfo.put("publisher", "출판사");
        bookInfo.put("year", String.valueOf(Year.now().getValue()));
        bookInfo.put("price", "25000");
        bookInfo.put("pages", "400");

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream("book_info.pdf"));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont("NanumGothicLight.ttf", PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
            document.setFont(font);

            for (String key : bookInfo.keySet()) {
                Paragraph paragraph = new Paragraph(key + ": " + bookInfo.get(key));
                document.add(paragraph);
            }

            document.close();

            System.out.println("pdf 파일 생성 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
