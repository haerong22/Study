package network.exception.connect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectTimeoutMain2 {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("192.168.1.250", 45678), 1000); // timeout 설정
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("end = " + (end - start));
    }

    /*
        java.net.SocketTimeoutException: Connect timed out

        ...

        end = 1011
     */
}
