package nio;

import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class _06_MemoryMappedFile {

    private static final String targetPath = "./tmp/nioTest.dat";

    private static void writeData(String data) throws Exception {
        FileOutputStream fos = new FileOutputStream(targetPath);
        FileChannel channel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        channel.write(buffer);
        channel.close();
    }

    public static void main(String[] args) {
        try {
            writeData("log test: memory mapped file sample");

            FileChannel channel;
            RandomAccessFile file = new RandomAccessFile(targetPath, "rw");
            channel = file.getChannel();

            long size = channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);

            byte[] data = new byte[(int) size];
            buffer.get(data);
            System.out.println(new String(data));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /*
        log test: memory mapped file sample
     */
}
