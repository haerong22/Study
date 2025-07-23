package socket;

import java.io.*;
import java.net.Socket;

public class _05_ChatClient {

    static class ChatMsgReceiver implements Runnable {
        private final BufferedReader serverReader;

        public ChatMsgReceiver(BufferedReader serverReader) {
            this.serverReader = serverReader;
        }

        @Override
        public void run() {
            try {
                String serverMsg;
                while ((serverMsg = serverReader.readLine()) != null) {
                    System.out.println("From server: " + serverMsg);
                }
            } catch (IOException e) {
                System.out.println("*** Disconnected from server ***");
            }
        }
    }

    public static void main(String[] args) {
        BufferedReader consoleInput = null;
        Socket socket = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            consoleInput = new BufferedReader(new InputStreamReader(System.in));

            socket = new Socket("127.0.0.1", 20000);
            System.out.println("*** Connected to server ***");

            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            Thread thread = new Thread(new ChatMsgReceiver(reader));
            thread.start();

            String msg;
            while ((msg = consoleInput.readLine()) != null) {
                if ("exit".equalsIgnoreCase(msg))
                    break;
                writer.println(msg);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
                if (consoleInput != null) consoleInput.close();
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                System.out.println("Exit");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
