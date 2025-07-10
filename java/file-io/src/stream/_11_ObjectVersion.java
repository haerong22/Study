package stream;

import java.io.*;

public class _11_ObjectVersion {

    private static final String targetPath = "./tmp/test.dat";

    public static DataObject readObject() {
        DataObject dto;
        try {
            FileInputStream fis = new FileInputStream(targetPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            dto = (DataObject) ois.readObject();
        } catch (Exception e) {
            return null;
        }

        return dto;
    }

    public static void main(String[] args) {
        DataObject dto = readObject();
        if(dto != null)
            System.out.println(dto);
        else
            System.out.println("Error: Failed to read object!");
    }

    /*
        Ver: 1
        Data: 10, TEST_DATA
     */
}