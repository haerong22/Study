package org.example.reactorpattern;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class TcpEventHandler implements EventHandler {
    private final ExecutorService executorService = Executors.newFixedThreadPool(50);
    private final Selector selector;
    private final SocketChannel clientSocket;

    @SneakyThrows
    public TcpEventHandler(Selector selector, SocketChannel clientSocket) {
        this.selector = selector;
        this.clientSocket = clientSocket;
        this.clientSocket.configureBlocking(false);
        this.clientSocket.register(this.selector, SelectionKey.OP_READ).attach(this);
    }

    @SneakyThrows
    @Override
    public void handle() {
        String requestBody = handleRequest(this.clientSocket);
        sendResponse(this.clientSocket, requestBody);
    }

    @SneakyThrows
    private String handleRequest(SocketChannel clientSocket) {
        ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
        clientSocket.read(requestByteBuffer);

        requestByteBuffer.flip();
        String requestBody = StandardCharsets.UTF_8.decode(requestByteBuffer).toString();

        log.info("request: {}", requestBody);

        return  requestBody;
    }

    private void sendResponse(SocketChannel clientSocket, String requestBody) {

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10);

                String content = "received: " + requestBody;
                ByteBuffer responseByteBuffer = ByteBuffer.wrap(content.getBytes());
                clientSocket.write(responseByteBuffer);
                clientSocket.close();
            } catch (Exception e) {
                //
            }
        }, executorService);
    }

}
