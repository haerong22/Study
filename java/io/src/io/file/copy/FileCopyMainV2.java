package io.file.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyMainV2 {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        FileInputStream fis = new FileInputStream("temp/copy.dat");
        FileOutputStream fos = new FileOutputStream("temp/copy_new.dat");

        fis.transferTo(fos); // java9
        fis.close();
        fos.close();

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }

    /*
        Time taken: 248ms
     */
}
