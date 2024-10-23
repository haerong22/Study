package network.exception.connect;

import java.io.IOException;
import java.net.Socket;

public class ConnectTimeoutMain1 {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        try {
            Socket socket = new Socket("192.168.1.250", 45678);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("end = " + (end - start));
    }

    /*
        java.net.ConnectException: Operation timed out

        ...

        end = 75024
     */
}
