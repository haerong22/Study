package network.exception.close.reset;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import static util.MyLogger.log;

public class ResetCloseClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        Socket socket = new Socket("localhost", 12345);
        log("소켓 연결: " + socket);
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        // client <- server: FIN
        Thread.sleep(1000);

        // client -> server: PUSH[1]
        output.write(1);

        // client <- server: RST (잘못된 연결이므로 즉시 종료!)
        Thread.sleep(1000);

        try {
            int read = input.read();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            output.write(1);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /*
        21:48:28.720 [     main] 소켓 연결: Socket[addr=localhost/127.0.0.1,port=12345,localport=50956]
        java.net.SocketException: Connection reset

        ...

        java.net.SocketException: Broken pipe

        ...
     */
}
