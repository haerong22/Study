package network.exception.close.normal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class NormalCloseServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(12345);
        Socket socket = serverSocket.accept();
        log("소켓 연결: " + socket);

        Thread.sleep(1000);
        socket.close();
        log("소켓 종료");
    }

    /*
        21:38:44.591 [     main] 소켓 연결: Socket[addr=/127.0.0.1,port=50773,localport=12345]
        21:38:45.598 [     main] 소켓 종료
     */
}
