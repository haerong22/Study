package util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getHash(String input) {
        StringBuffer result = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            byte bytes[] = md.digest();
            for (byte aByte : bytes) {
                result.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        Date time = new Date();

        String nowTime = format.format(time);
        return nowTime;
    }
}
