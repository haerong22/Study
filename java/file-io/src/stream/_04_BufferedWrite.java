package stream;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class _04_BufferedWrite {

    public static void main(String[] args) {
        try (OutputStream os =
                     new BufferedOutputStream(new FileOutputStream("./tmp/test.dat"), 65536)) {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 1024 * 1024; i++) {
                os.write(0XFF);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Duration(ms): " + (endTime - startTime));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /*
        Duration(ms): 22
     */
}
