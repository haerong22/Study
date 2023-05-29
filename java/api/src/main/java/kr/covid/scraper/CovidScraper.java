package kr.covid.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CovidScraper {

    public static void main(String[] args) {
        final String url = "https://ncov.kdca.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=13";

        try {

            Document document = Jsoup.connect(url).get();

            String date = document.select("div.timetable > p").first().text();

            Element table = document.select("table.num").first();
            Elements rows = table.select("tbody > tr");

            List<CovidStatus> covidStatusList = new ArrayList<>();

            for (Element row : rows) {

                String region = row.select("th").text();
                int total = Integer.parseInt(row.select("td:nth-child(2)").text().replaceAll(",", ""));
                int domestic = Integer.parseInt(row.select("td:nth-child(3)").text().replaceAll(",", ""));
                int abroad = Integer.parseInt(row.select("td:nth-child(4)").text().replaceAll(",", ""));
                int confirmed = Integer.parseInt(row.select("td:nth-child(5)").text().replaceAll(",", ""));
                int deaths = Integer.parseInt(row.select("td:nth-child(6)").text().replaceAll(",", ""));

                String rateStr = row.select("td:nth-child(7)").text().replaceAll(",", "");
                double rate = rateStr.equals("-") ? 0.0 : Double.parseDouble(rateStr);

                covidStatusList.add(new CovidStatus(region, total, domestic, abroad, confirmed, deaths, rate));
            }

            System.out.println("일일 코로나 바이러스 감염 현황 (" + date + ")");
            System.out.println("시도 | 합계 | 국내발생 | 해외유입 | 확진환자 | 사망자 | 발생률");

            for (CovidStatus covidStatus : covidStatusList) {
                System.out.println(covidStatus);
            }

            String excelFilename = "covid_status_" + date.replace(" ", "_").replace(":", "_") + ".xlsx";
            ExcelExporter.exportToExcel(date, covidStatusList, excelFilename);
            System.out.println("엑셀 파일로 저장 완료: " + excelFilename);

            String pdfFilename = "covid_status_" + date.replace(" ", "_").replace(":", "_") + ".pdf";
            PdfExporter.exportToPdf(date, covidStatusList, pdfFilename);
            System.out.println("PDF 파일로 저장 완료: " + pdfFilename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
