package network.tcp.v2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class ClientV2 {

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
        22:00:32.096 [     main] 클라이언트 시작
        22:00:32.103 [     main] 소켓 연결: Socket[addr=localhost/127.0.0.1,port=12345,localport=51341]
        전송 문자: hello
        22:00:36.223 [     main] client -> server: hello
        22:00:36.224 [     main] client <- server: hello World!
        전송 문자: hi
        22:00:37.225 [     main] client -> server: hi
        22:00:37.226 [     main] client <- server: hi World!
        전송 문자: exit
        22:00:39.500 [     main] client -> server: exit
        22:00:39.501 [     main] 연결 종료: Socket[addr=localhost/127.0.0.1,port=12345,localport=51341]
     */
}
