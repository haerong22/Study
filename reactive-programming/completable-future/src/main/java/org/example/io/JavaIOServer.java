package org.example.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class JavaIOServer {

    public static void main(String[] args) {
        log.info("start main");

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));

            while (true) {
                Socket clientSocket = serverSocket.accept();

                byte[] responseBytes = new byte[1024];
                InputStream in = clientSocket.getInputStream();

                in.read(responseBytes);
                log.info("request: {}", new String(responseBytes).trim());

                String response = "This is server";
                OutputStream out = clientSocket.getOutputStream();
                out.write(response.getBytes());
                out.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
