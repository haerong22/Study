package nio;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class _03_SingleChannel {

    private static final String targetPath = "./tmp/single.dat";
    private static FileChannel channel;

    private static void initChannel() throws Exception {
        channel = FileChannel.open(
                Paths.get(targetPath),
                StandardOpenOption.TRUNCATE_EXISTING, // size 0
                StandardOpenOption.CREATE,
                StandardOpenOption.READ,
                StandardOpenOption.WRITE
        );
    }

    private static void readData(int length) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        if (channel.read(buffer) > 0) {
            byte[] data = new byte[length];
            buffer.get(0, data);
            System.out.println("readData(): " + new String(data));
        }
    }

    private static int writeData(String data) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        return channel.write(buffer);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            initChannel();
            String input;
            for (input = scanner.nextLine();
                 !input.equals("exit");
                 input = scanner.nextLine()) {
                int length = writeData(input);
                channel.position(channel.position() - length);
                readData(length);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
        Hello world!!
        readData(): Hello world!!
        Test String
        readData(): Test String
        exit
     */
}
