package stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class _12_Transient {

    private static final String targetPath = "./tmp/test.dat";

    public static boolean writeObject(DataObject dto) {
        try {
            FileOutputStream fos = new FileOutputStream(targetPath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dto);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

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
        if (writeObject(new DataObject(10, "TEST_DATA"))) {
            System.out.println("writeObject(): true");
        } else {
            System.out.println("writeObject(): false");
        }

        DataObject dto = readObject();
        if (dto != null) {
            System.out.println(dto);
        } else {
            System.out.println("Error: Failed to read object!");
        }
    }

    /*
        writeObject(): true
        Ver: 1
        Data: 0, TEST_DATA
     */
}