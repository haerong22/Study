package io.text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static io.text.TextConst.FILE_NAME;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ReaderWriterMainV1 {

    public static void main(String[] args) throws IOException {
        String writeString = "ABC";

        // String -> byte UTF-8 encoding
        byte[] writeBytes = writeString.getBytes(UTF_8);
        System.out.println("write String: " + writeString);
        System.out.println("write bytes: " + Arrays.toString(writeBytes));

        // 파일에 쓰기
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        fos.write(writeBytes);
        fos.close();

        // 파일에서 읽기
        FileInputStream fis = new FileInputStream(FILE_NAME);
        byte[] readBytes = fis.readAllBytes();
        fis.close();

        // byte -> String UTF-8 decoding
        String readString = new String(readBytes, UTF_8);
        System.out.println("read bytes: " + Arrays.toString(readBytes));
        System.out.println("read String: " + readString);
    }

    /*
        write String: ABC
        write bytes: [65, 66, 67]
        read bytes: [65, 66, 67]
        read String: ABC
     */
}
