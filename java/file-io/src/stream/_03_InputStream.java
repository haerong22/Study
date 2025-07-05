package stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class _03_InputStream {

    private static void makeFile() throws Exception {
        OutputStream os = new FileOutputStream("./tmp/test.dat");
        byte[] data = {'A', 'B', 'C', 'D', 'E', 'F'};
        os.write(data);
        os.close();
    }

    public static void main(String[] args) {
        try {
            makeFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileInputStream input = new FileInputStream("./tmp/test.dat")){
            int data;
            while ((data = input.read()) != -1) {
                System.out.print((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
        ABCDEF
     */
}
