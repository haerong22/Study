package io.start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * read()가 int를 반환하는 이유
 * 자바의 byte : -128 ~ 127
 * EOF 표시 (-1)
 */
public class StreamStartMain1 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp/hello.dat");
        fos.write(65);
        fos.write(66);
        fos.write(67);

        fos.close();

        FileInputStream fis = new FileInputStream("temp/hello.dat");
        System.out.println(fis.read());
        System.out.println(fis.read());
        System.out.println(fis.read());
        System.out.println(fis.read());
        fis.close();
    }

    /*
        65
        66
        67
        -1
     */
}
