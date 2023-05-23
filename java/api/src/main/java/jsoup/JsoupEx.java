package jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupEx {

    public static void main(String[] args) {
        final String url = "https://sum.su.or.kr:8888/bible/today?base_de=2023-05-22";

        Document document = null;
        try {
            document = Jsoup.connect(url).get();

            Element bibleText = document.getElementById("bible_text");
            Element bibleInfoBox = document.getElementById("bibleinfo_box");

            System.out.println("bibleText = " + bibleText.text());
            System.out.println("bibleInfoBox = " + bibleInfoBox.text());

            Elements num = document.select(".num");
            Elements info = document.select(".info");

            for (int i = 0; i < num.size(); i++) {
                System.out.println(num.get(i).text() + " : " + info.get(i).text());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
