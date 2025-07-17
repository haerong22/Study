package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;

public class _05_AsyncChannel {

    public static void main(String[] args) throws IOException, InterruptedException {
        Path path = Paths.get("./tmp/nioAsync.txt");
        AsynchronousFileChannel asyncChannel = AsynchronousFileChannel.open(
                path,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE
        );

        String data = "AsynchronousFileChannel I/O sample";

        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        CountDownLatch latch = new CountDownLatch(2);

        // 쓰기 '요청' -> jvm -> os
        asyncChannel.write(buffer, 1024 * 1024 * 1024, "Async write (1GB): ",
                new CompletionHandler<Integer, String>() {
                    @Override
                    public void completed(Integer result, String attachment) {
                        System.out.println(attachment + result + " bytes");
                        latch.countDown();
                    }

                    @Override
                    public void failed(Throwable exc, String attachment) {
                        System.out.println("ERROR: " + exc.getMessage());
                        latch.countDown();
                    }
                });

        ByteBuffer buffer2 = ByteBuffer.wrap(data.getBytes());
        asyncChannel.write(buffer2, 1024, "Async write (1MB): ",
                new CompletionHandler<Integer, String>() {
                    @Override
                    public void completed(Integer result, String attachment) {
                        System.out.println(attachment + result + " bytes");
                        latch.countDown();
                    }

                    @Override
                    public void failed(Throwable exc, String attachment) {
                        System.out.println("ERROR: " + exc.getMessage());
                        latch.countDown();
                    }
                });

        System.out.println("Waiting...");
        latch.await();
        System.out.println("End of main thread.");
    }

    /*
        Waiting...
        Async write (1MB): 0 bytes
        Async write (1GB): 34 bytes
        End of main thread.
     */

}
