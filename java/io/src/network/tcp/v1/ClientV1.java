package network.tcp.v1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.MyLogger.log;

public class ClientV1 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");

        Socket socket = new Socket("localhost", PORT);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        log("소켓 연결: " + socket);

        // 서버로 문자 보내기
        String toSend = "Hello";
        output.writeUTF(toSend);
        log("client -> server: " + toSend);

        // 서버로부터 문자 받기
        String received = input.readUTF();
        log("client <- server: " + received);

        // 자원 정리
        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close();
    }

    /*
        21:12:08.193 [     main] 클라이언트 시작
        21:12:08.201 [     main] 소켓 연결: Socket[addr=localhost/127.0.0.1,port=12345,localport=50094]
        21:12:08.202 [     main] client -> server: Hello
        21:12:08.204 [     main] client <- server: Hello World!
        21:12:08.204 [     main] 연결 종료: Socket[addr=localhost/127.0.0.1,port=12345,localport=50094]
     */
}
