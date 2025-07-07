package stream;

import java.io.FileReader;
import java.io.Reader;

public class _07_ReaderString {

    public static void main(String[] args) {
        try (Reader reader = new FileReader("./tmp/test.txt")) {
            int data;

            while ((data = reader.read()) != -1) {
                System.out.print((char) data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
        Hello world
     */
}
