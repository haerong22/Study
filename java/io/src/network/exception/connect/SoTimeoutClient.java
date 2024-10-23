package network.exception.connect;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SoTimeoutClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        InputStream input = socket.getInputStream();

        try {
            socket.setSoTimeout(3000);  // 타임아웃 시간 설정, 안하면 무한 대기
            int read = input.read();
            System.out.println("read = " + read);
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.close();
    }

    /*
        java.net.SocketTimeoutException: Read timed out

        ...

     */
}
