package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class _01_EchoServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        int listenPort = 20000;

        try {
            System.out.println("Server start...");
            serverSocket = new ServerSocket(listenPort);

            clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            InputStream inputStream = clientSocket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            OutputStream outputStream = clientSocket.getOutputStream();
            writer = new PrintWriter(outputStream, true);

            String msg;
            while ((msg = reader.readLine()) != null) {
                if ("exit".equalsIgnoreCase(msg))
                    break;

                System.out.println("Received: " + msg);
                writer.println(msg);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
                if (reader != null) reader.close();
                if (writer != null) writer.close();

                System.out.println("Server stop...");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /*
        Server start...
        Client connected
        Received: Hello echo server
        Received: bye
        Server stop...
     */
}
