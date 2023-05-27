package kr.book.search;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class PdfGenerator {

    public static void generateBookListPdf(List<Book> books, String filename) throws FileNotFoundException {
        PdfWriter pdfWriter = new PdfWriter(filename);
        PdfDocument pdf = new PdfDocument(pdfWriter);
        Document document = new Document(pdf);
        document.setFontSize(12);

        PdfFont font;

        try {
            font = PdfFontFactory.createFont("NanumGothicLight.ttf", PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
            document.setFont(font);

            Paragraph titleParagraph = new Paragraph("도서 목록");
            titleParagraph.setFontSize(24);
            titleParagraph.setTextAlignment(TextAlignment.CENTER);
            titleParagraph.setBold();
            document.add(titleParagraph);

            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setMarginTop(20);

            table.addHeaderCell(createCell("제목", true));
            table.addHeaderCell(createCell("저자", true));
            table.addHeaderCell(createCell("출판사", true));
            table.addHeaderCell(createCell("이미지", true));

            for (Book book : books) {
                table.addCell(createCell(book.getTitle(), false));
                table.addCell(createCell(book.getAuthors(), false));
                table.addCell(createCell(book.getPublisher(), false));

                try {
                    ImageData imageData = ImageDataFactory.create(book.getThumbnail());
                    Image image = new Image(imageData);
                    image.setAutoScale(true);
                    table.addCell(new Cell().add(image).setPadding(5));
                } catch (Exception e) {
                    table.addCell(createCell("이미지 불러오기 실패", false));
                }
            }

            document.add(table);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Cell createCell(String content, boolean isHeader) {
        Paragraph paragraph = new Paragraph(content);
        Cell cell = new Cell().add(paragraph);
        cell.setPadding(5);

        if (isHeader) {
            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            cell.setFontSize(14);
            cell.setBold();
        }
        return cell;
    }
}
