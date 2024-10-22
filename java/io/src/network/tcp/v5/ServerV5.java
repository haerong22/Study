package network.tcp.v5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ServerV5 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            log("소켓 연결: " + socket);

            SessionV5 session = new SessionV5(socket);
            Thread thread = new Thread(session);
            thread.start();
        }
    }

    /*
        21:21:13.026 [     main] 서버 시작
        21:21:13.028 [     main] 서버 소켓 시작 - 리스닝 포트: 12345
        21:21:21.311 [     main] 소켓 연결: Socket[addr=/127.0.0.1,port=51623,localport=12345]
        21:21:23.773 [ Thread-0] java.io.EOFException
        21:21:23.779 [ Thread-0] 연결 종료: Socket[addr=/127.0.0.1,port=51623,localport=12345] isClosed: true
     */
}
