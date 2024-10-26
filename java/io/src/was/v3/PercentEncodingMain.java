package was.v3;

import java.net.URLDecoder;
import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PercentEncodingMain {

    public static void main(String[] args) {
        String encoded = URLEncoder.encode("가", UTF_8);
        System.out.println("encoded = " + encoded);

        String decoded = URLDecoder.decode(encoded, UTF_8);
        System.out.println("decode = " + decoded);
    }
}
