package network.tcp.v3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ServerV3 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            log("소켓 연결: " + socket);

            SessionV3 session = new SessionV3(socket);
            Thread thread = new Thread(session);
            thread.start();
        }
    }

    /*
        22:38:38.901 [     main] 서버 시작
        22:38:38.903 [     main] 서버 소켓 시작 - 리스닝 포트: 12345
        22:38:44.365 [     main] 소켓 연결: Socket[addr=/127.0.0.1,port=52241,localport=12345]
        22:38:55.846 [     main] 소켓 연결: Socket[addr=/127.0.0.1,port=52248,localport=12345]
        22:39:05.716 [ Thread-0] client -> server: hello
        22:39:05.718 [ Thread-0] client <- server: hello World!
        22:39:08.831 [ Thread-1] client -> server: hihi
        22:39:08.831 [ Thread-1] client <- server: hihi World!
        22:39:24.878 [ Thread-1] client -> server: hihi2
        22:39:24.879 [ Thread-1] client <- server: hihi2 World!
        22:39:27.725 [ Thread-0] client -> server: hello2
        22:39:27.725 [ Thread-0] client <- server: hello2 World!
        22:40:23.194 [ Thread-0] client -> server: exit
        22:40:23.196 [ Thread-0] 연결 종료: Socket[addr=/127.0.0.1,port=52241,localport=12345]
        22:40:27.917 [ Thread-1] client -> server: exit
        22:40:27.927 [ Thread-1] 연결 종료: Socket[addr=/127.0.0.1,port=52248,localport=12345]
     */
}
