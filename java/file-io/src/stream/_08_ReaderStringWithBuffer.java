package stream;

import java.io.FileReader;
import java.io.Reader;

public class _08_ReaderStringWithBuffer {

    public static void main(String[] args) {
        try (Reader reader = new FileReader("./tmp/test.txt")) {
            char[] buffer = new char[20];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, read));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
        Hello world
     */
}
