package charset;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.SortedMap;

public class AvailableCharsetsMain {

    public static void main(String[] args) {
        // 이용 가능한 모든 Charset 자바 + OS
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for (String charsetName : charsets.keySet()) {
            System.out.println("charsetName = " + charsetName);
        }

        System.out.println("=================================");

        // 문자로 조회(대소문자 구분X)
        Charset charset1 = Charset.forName("MS949");
        System.out.println("charset1 = " + charset1);

        // 별칭 조회
        Set<String> aliases = charset1.aliases();
        for (String alias : aliases) {
            System.out.println("alias = " + alias);
        }

        // UTF-8 문자로 조회
        Charset charset2 = Charset.forName("UTF-8");
        System.out.println("charset2 = " + charset2);

        // UTF-8 상수로 조회
        Charset charset3 = StandardCharsets.UTF_8;
        System.out.println("charset3 = " + charset3);

        // 시스템의 기본 Charset 조회
        Charset defaultCharset = Charset.defaultCharset();
        System.out.println("defaultCharset = " + defaultCharset);
    }

    /*
        charsetName = Big5
        charsetName = Big5-HKSCS

        ...

        charsetName = x-windows-949
        charsetName = x-windows-950
        charsetName = x-windows-iso2022jp
        =================================
        charset1 = x-windows-949
        alias = ms949
        alias = windows949
        alias = windows-949
        alias = ms_949
        charset2 = UTF-8
        charset3 = UTF-8
        defaultCharset = UTF-8
     */
}
