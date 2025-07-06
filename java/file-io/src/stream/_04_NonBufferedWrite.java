package stream;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class _04_NonBufferedWrite {

    public static void main(String[] args) {
        try (OutputStream os = new FileOutputStream("./tmp/test.dat")) {
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
        Duration(ms): 1608
     */
}
