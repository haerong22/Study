package network.tcp.v2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ServerV2 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        Socket socket = serverSocket.accept();
        log("소켓 연결: " + socket);

        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        while (true) {
            // 클라이언트로부터 문자 받기
            String received = input.readUTF();
            log("client -> server: " + received);

            if (received.equals("exit")) {
                break;
            }

            // 클라이언트에게 문자 보내기
            String toSend = received + " World!";
            output.writeUTF(toSend);
            log("client <- server: " + toSend);
        }

        // 자원 정리
        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close();
        serverSocket.close();
    }

    /*
        22:00:28.638 [     main] 서버 시작
        22:00:28.639 [     main] 서버 소켓 시작 - 리스닝 포트: 12345
        22:00:32.104 [     main] 소켓 연결: Socket[addr=/127.0.0.1,port=51341,localport=12345]
        22:00:36.223 [     main] client -> server: hello
        22:00:36.224 [     main] client <- server: hello World!
        22:00:37.226 [     main] client -> server: hi
        22:00:37.226 [     main] client <- server: hi World!
        22:00:39.501 [     main] client -> server: exit
        22:00:39.501 [     main] 연결 종료: Socket[addr=/127.0.0.1,port=51341,localport=12345]
     */
}
