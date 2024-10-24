package network.exception.close.normal;

import java.io.*;
import java.net.Socket;

import static util.MyLogger.log;

/*
    서버에서 close -> FIN 패킷 보냄 -> 클라이언트도 close
 */
public class NormalCloseClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        log("소켓 연결: " + socket);
        InputStream input = socket.getInputStream();

//        readByInputStream(input, socket);
//        readByBufferedReader(input, socket);
        readByDataInputStream(input, socket);

        log("연결 종료: " + socket.isClosed());
    }

    /*
        21:38:44.585 [     main] 소켓 연결: Socket[addr=localhost/127.0.0.1,port=12345,localport=50773]
        21:38:45.620 [     main] read = -1
        21:38:45.621 [     main] 연결 종료: true

        21:39:40.588 [     main] 소켓 연결: Socket[addr=localhost/127.0.0.1,port=12345,localport=50797]
        21:39:41.596 [     main] readString = null
        21:39:41.602 [     main] 연결 종료: true

        21:39:56.437 [     main] 소켓 연결: Socket[addr=localhost/127.0.0.1,port=12345,localport=50805]
        21:39:57.444 [     main] java.io.EOFException
        21:39:57.448 [     main] 연결 종료: true
     */

    private static void readByInputStream(InputStream input, Socket socket) throws IOException {
        int read = input.read();
        log("read = " + read);
        if (read == -1) {
            input.close();
            socket.close();
        }
    }

    private static void readByBufferedReader(InputStream input, Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String readString = br.readLine();
        log("readString = " + readString);
        if (readString == null) {
            input.close();
            socket.close();
        }
    }

    private static void readByDataInputStream(InputStream input, Socket socket) throws IOException {
        DataInputStream dis = new DataInputStream(input);

        try {
            dis.readUTF();
        } catch (IOException e) {
            log(e);
        } finally {
            dis.close();
            socket.close();
        }
    }
}
