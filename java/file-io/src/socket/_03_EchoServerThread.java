package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class _03_EchoServerThread {

    public static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private BufferedReader reader = null;
        private PrintWriter writer = null;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                OutputStream outputStream = clientSocket.getOutputStream();
                writer = new PrintWriter(outputStream, true);

                String msg;
                while ((msg = reader.readLine()) != null) {
                    if ("exit".equalsIgnoreCase(msg))
                        break;

                    System.out.println(msg);
                    writer.println(msg);
                }
            } catch (IOException e) {
                System.out.println("Client exception: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                    if (reader != null) reader.close();
                    if (writer != null) writer.close();

                    System.out.println("Disconnected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                } catch (IOException e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int listenPort = 20000;

        try {
            System.out.println("Server start...");
            serverSocket = new ServerSocket(listenPort);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected...");
                System.out.println("[" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + "]");

                ClientHandler handler = new ClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
                System.out.println("Server stop...");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
