package stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class _09_DataStream {

    private static void makeFile() throws Exception {
        FileOutputStream os = new FileOutputStream("./tmp/test.dat");
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeDouble(3.14);
        dos.writeInt(-50);
        dos.writeUTF("Hello world!");
        dos.close();
    }

    public static void main(String[] args) throws Exception {
        makeFile();
        FileInputStream is = new FileInputStream("./tmp/test.dat");
        DataInputStream dis = new DataInputStream(is);

        System.out.println(dis.readDouble());
        System.out.println(dis.readInt());
        System.out.println(dis.readUTF());
        dis.close();
    }
    /*
        3.14
        -50
        Hello world!
     */
}
