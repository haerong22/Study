package stream;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class _02_OutputArray {

    public static void main(String[] args) throws Exception {
        OutputStream os = new FileOutputStream("./tmp/test.dat");
        byte[] data = {16, 32, 48, 64, 80, 96};
        os.write(data, 3, 2); // 64, 80 write
        os.close();
    }
}
