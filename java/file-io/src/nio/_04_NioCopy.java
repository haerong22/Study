package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class _04_NioCopy {

    public static void main(String[] args) {
        String srcPath = "./tmp/src.txt";
        String dstPath = "./tmp/dst.txt";

        try {
            FileChannel src = new FileInputStream(srcPath).getChannel();
            FileChannel dst = new FileOutputStream(dstPath).getChannel();
            dst.transferFrom(src, 0, src.size()); // zero copy
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
