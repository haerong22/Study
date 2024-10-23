package network.exception.connect;

import java.io.IOException;
import java.net.Socket;

public class ConnectMain {

    public static void main(String[] args) {
        unknownHostEx1();
        unknownHostEx2();
        connectionRefused();
    }

    /*
        java.net.UnknownHostException: 999.999.999.999
        ...
        java.net.UnknownHostException: google.google
        ...
        java.net.ConnectException: Connection refused
        ...
     */

    // UnknownHostException: 호스트를 알수 없음 (존재하지 않는 IP)
    private static void unknownHostEx1() {
        try {
            Socket socket = new Socket("999.999.999.999", 80);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // UnknownHostException: 호스트를 알수 없음 (존재하지 않는 도메인 이름)
    private static void unknownHostEx2() {
        try {
            Socket socket = new Socket("google.google", 80);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ConnectException: 해당 서버는 켜져 있지만 연결이 거절
    private static void connectionRefused() {
        try {
            Socket socket = new Socket("localhost", 45678);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
