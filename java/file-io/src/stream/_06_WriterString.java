package stream;

import java.io.FileWriter;
import java.io.IOException;

public class _06_WriterString {

    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("./tmp/test.txt");

        char[] string = {'H', 'e', 'l', 'l', 'o'};
        writer.write(string);
        writer.write(" world");
        writer.close();
    }
}
