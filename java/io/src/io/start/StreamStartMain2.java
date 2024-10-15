package io.start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamStartMain2 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp/hello.dat");
        fos.write(65);
        fos.write(66);
        fos.write(67);

        fos.close();

        FileInputStream fis = new FileInputStream("temp/hello.dat");
        int data = 0;
        while ((data = fis.read()) != -1) {
            System.out.println(data);
        }
        fis.close();
    }

    /*
        65
        66
        67
     */
}
