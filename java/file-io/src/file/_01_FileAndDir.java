package file;

import java.io.File;
import java.io.IOException;

public class _01_FileAndDir {

    public static void main(String[] args) {
        String tmpPath = "./tmp";
        File targetDir = new File(tmpPath);
        if (!targetDir.exists()) {
            targetDir.mkdir();
        }

        File testFile01 = new File(tmpPath + "/test01.txt");
        File testFile02 = new File(tmpPath + "/test02.txt");

        try {
            testFile01.createNewFile();
            testFile02.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] fileList = targetDir.list();
        for (String name : fileList) {
            System.out.println("name = " + name);
        }
    }

    /*
        name = test01.txt
        name = test02.txt
     */
}
