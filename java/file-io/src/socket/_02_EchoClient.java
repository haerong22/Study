package socket;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class _02_EchoClient {

    public static void main(String[] args) {
        BufferedReader consoleInput = null;
        Socket socket = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            consoleInput = new BufferedReader(new InputStreamReader(System.in));

            socket = new Socket("127.0.0.1", 20000);
            ;
            System.out.println("*** Connected to server ***");

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

            String msg;
            while ((msg = consoleInput.readLine()) != null) {
                if ("exit".equalsIgnoreCase(msg)) {
                    break;
                }

                writer.println(msg);
                String response = reader.readLine();
                System.out.println("From server: " + response);
            }
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
                if (consoleInput != null) consoleInput.close();
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    /*
        *** Connected to server ***
        Hello echo server
        From server: Hello echo server
        bye
        From server: bye
        exit
     */
}
