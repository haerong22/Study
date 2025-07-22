package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class _04_ChatServer {

    public static class ClientHandler implements Runnable {

        private final Socket clientSocket;
        private BufferedReader reader = null;
        private PrintWriter writer = null;

        private static final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            clients.add(this);
        }

        private void senMessageAll(String msg) {
            for (ClientHandler client : clients) {
                client.writer.println(msg);
            }
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
                    senMessageAll(msg);
                }
            } catch (IOException e) {
                System.out.println("Client exception: " + e.getMessage());
            } finally {
                try {
                    if (reader != null) reader.close();
                    if (writer != null) writer.close();
                    if (clientSocket != null) clientSocket.close();

                    System.out.println("Disconnected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                } catch (IOException ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int listenPort = 20000;

        try {
            System.out.println("Server Start...");
            serverSocket = new ServerSocket(listenPort);

            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("New Client Connected...");
                System.out.println("[" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + "]");

                ClientHandler handler = new ClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start();
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {

            try {
                if (serverSocket != null) serverSocket.close();
                System.out.println("Server stop...");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}