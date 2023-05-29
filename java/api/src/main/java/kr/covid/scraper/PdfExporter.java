package kr.covid.scraper;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.util.List;

public class PdfExporter {

    public static void exportToPdf(String date, List<CovidStatus> covidStatusList, String filename) {

        try {
            PdfFont font = PdfFontFactory.createFont("NanumGothicLight.ttf", PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);

            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(filename));
            Document document = new Document(pdfDocument);

            Paragraph title = new Paragraph("일일 코로나 바이러스 감염 현황 (" + date + ")");
            document.add(title.setFont(font));

            float[] columnWidths = {100, 50, 50, 50, 50, 50, 50};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));

            String[] headers = {"시도", "합계", "국내발생", "해외유입", "확진환자", "사망자", "발생률"};

            for (String header : headers) {
                Cell cell = new Cell();
                cell.add(new Paragraph(header).setFont(font));
                cell.setTextAlignment(TextAlignment.CENTER);
                table.addHeaderCell(cell);
            }

            for (CovidStatus covidStatus : covidStatusList) {
                table.addCell(new Cell().add(new Paragraph(covidStatus.getRegion()).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(covidStatus.getTotal())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(covidStatus.getDomestic())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(covidStatus.getAbroad())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(covidStatus.getConfirmed())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(covidStatus.getDeaths())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", covidStatus.getRate())).setFont(font)));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
