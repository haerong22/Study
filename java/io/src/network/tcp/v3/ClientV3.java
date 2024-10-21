package network.tcp.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class ClientV3 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");

        Socket socket = new Socket("localhost", PORT);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        log("소켓 연결: " + socket);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("전송 문자: ");
            String toSend = scanner.nextLine();

            // 서버로 문자 보내기
            output.writeUTF(toSend);
            log("client -> server: " + toSend);

            if (toSend.equals("exit")) {
                break;
            }

            // 서버로부터 문자 받기
            String received = input.readUTF();
            log("client <- server: " + received);
        }

        // 자원 정리
        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close();
    }

    /*
        [client1]
        22:38:44.357 [     main] 클라이언트 시작
        22:38:44.364 [     main] 소켓 연결: Socket[addr=localhost/127.0.0.1,port=12345,localport=52241]
        전송 문자: hello
        22:39:05.715 [     main] client -> server: hello
        22:39:05.718 [     main] client <- server: hello World!
        전송 문자: hello2
        22:39:27.725 [     main] client -> server: hello2
        22:39:27.726 [     main] client <- server: hello2 World!
        전송 문자: exit
        22:40:23.193 [     main] client -> server: exit
        22:40:23.195 [     main] 연결 종료: Socket[addr=localhost/127.0.0.1,port=12345,localport=52241]

        [client2]
        22:38:55.839 [     main] 클라이언트 시작
        22:38:55.845 [     main] 소켓 연결: Socket[addr=localhost/127.0.0.1,port=12345,localport=52248]
        전송 문자: hihi
        22:39:08.831 [     main] client -> server: hihi
        22:39:08.832 [     main] client <- server: hihi World!
        전송 문자: hihi2
        22:39:24.874 [     main] client -> server: hihi2
        22:39:24.880 [     main] client <- server: hihi2 World!
        전송 문자: exit
        22:40:27.891 [     main] client -> server: exit
        22:40:27.917 [     main] 연결 종료: Socket[addr=localhost/127.0.0.1,port=12345,localport=52248]
     */
}
