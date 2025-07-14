package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class _02_FileChannel {

    private static final String targetPath = "./tmp/nioTest.dat";
    private static FileOutputStream fos;
    private static FileInputStream fis;
    private static FileChannel writeChannel;
    private static FileChannel readChannel;

    private static void initChannels() throws Exception {
        fos = new FileOutputStream(targetPath);
        writeChannel = fos.getChannel();
        fis = new FileInputStream(targetPath);
        readChannel = fis.getChannel();
    }

    private static void readData() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int length = readChannel.read(buffer);
        if (length > 0) {
            byte[] data = new byte[length];
            buffer.get(0, data);
            System.out.println("readData(): " + new String(data));
        }
    }

    private static void writeData(String data) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        writeChannel.write(buffer);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            initChannels();
            String input;
            for (input = scanner.nextLine();
                 !input.equals("exit");
                 input = scanner.nextLine()) {
                writeData(input);
                readData();
            }
            readChannel.close();
            writeChannel.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /*
        Test String
        readData(): Test String
        Hello
        readData(): Hello
        exit
     */
}
