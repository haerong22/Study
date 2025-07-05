package stream;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class _01_OutputStream {

    public static void main(String[] args) throws Exception {
        OutputStream os = new FileOutputStream("./tmp/test.dat");
        byte[] data = new byte[3];
        data[0] = 16;
        data[1] = 32;
        data[2] = 64;
        os.write(data);
        os.close();
    }
}
