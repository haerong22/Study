package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class _03_FileAccess {

    private static FileWriter writer;

    private static void initTargetFile() throws Exception {
        writer = new FileWriter("./tmp/test.txt");
    }

    private static void writeData(String data) throws IOException {
        FileWriter writer = new FileWriter("./tmp/test.txt");
        for (int i = 0; i < 1000; i++) {
            writer.write(data);
            writer.flush();
        }
    }

    public static void main(String[] args) throws Exception {
        File targetDir = new File("./tmp");
        if (!targetDir.exists()) {
            targetDir.mkdir();
        }
        initTargetFile();

        Thread t1 = new Thread(() -> {
            try {
                writeData("Hello\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                writeData("World\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        writer.close();
        System.out.println("main() - end");
    }
}
